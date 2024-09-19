package com.azo.backend.msvc.binnacle.msvc_binnacle.services;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.azo.backend.msvc.binnacle.msvc_binnacle.clients.UserClientRest;
import com.azo.backend.msvc.binnacle.msvc_binnacle.enums.RequestStatus;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.User;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.DocumentDto;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.RequestDetailDto;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.RequestDto;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.mapper.DtoMapperRequest;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.entities.Document;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.entities.Request;
import com.azo.backend.msvc.binnacle.msvc_binnacle.repositories.DocumentRepository;
import com.azo.backend.msvc.binnacle.msvc_binnacle.repositories.RequestRepository;

//paso 4.1.
@Service
public class RequestServiceImpl implements RequestService {

  @Autowired
  private RequestRepository repository;

  @Autowired
  private DocumentRepository documentRepository;

  @Autowired
  private UserClientRest userClientRest;

  @Override
  @Transactional(readOnly = true)
  public List<RequestDto> findAll() {
    List<Request> requests = (List<Request>) repository.findAll();
    return requests
      .stream()
      .map( r -> DtoMapperRequest.builder().setRequest(r).build())
      .collect(Collectors.toList());
  }

  @Override
  @Transactional(readOnly = true)
  public Page<RequestDto> findAll(Pageable pageable) {
    Page<Request> requestsPage = repository.findAll(pageable);
    return requestsPage.map(r -> DtoMapperRequest.builder().setRequest(r).build());
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<RequestDetailDto> findById(Long id) {
    return repository.findById(id).map(r -> {
      User citizen = userClientRest.detail(r.getCitizenId());
      User assignedUser = userClientRest.detail(r.getAssignedToUserId());
      return DtoMapperRequest.builder()
              .setRequest(r)
              .setCitizen(citizen)
              .setAssignedUser(assignedUser)
              .buildDetailDto();
    });
    //return repository.findById(id);
  }

  @Override
  @Transactional
  public RequestDto save(RequestDto requestDto) {
    Request request = new Request();
    request.setEntryDate(requestDto.getEntryDate());
    request.setStatus(requestDto.getStatus());
    request.setCitizenId(requestDto.getCitizenId());
    request.setCadastralCode(requestDto.getCadastralCode());
    request.setAssignedToUserId(requestDto.getAssignedToUserId());

    request = repository.save(request);

    // Manejar documentos
    if (requestDto.getDocuments() != null && !requestDto.getDocuments().isEmpty()) {
      List<Document> documents = requestDto.getDocuments().stream()
        .map(docDto -> {
            Document doc = new Document();
            doc.setType(docDto.getType());
            doc.setUploadDate(docDto.getUploadDate());
            doc.setFileUrl(docDto.getFileUrl());
            doc.setRequest(docDto.getRequest());
            return doc;
        })
        .collect(Collectors.toList());
      documentRepository.saveAll(documents);
      request.setDocuments(documents);
    }
    return DtoMapperRequest.builder().setRequest(request).build();
  }

  @Override
  @Transactional
  public RequestDto update(Long id, RequestDto requestDto) {
    Request existingRequest = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Request not found"));
    
    existingRequest.setStatus(requestDto.getStatus());
    existingRequest.setCadastralCode(requestDto.getCadastralCode());
    existingRequest.setAssignedToUserId(requestDto.getAssignedToUserId());
    
    // Actualizar documentos
    if (requestDto.getDocuments() != null) {
      updateDocuments(existingRequest, requestDto.getDocuments());
    }

    existingRequest = repository.save(existingRequest);
    return DtoMapperRequest.builder().setRequest(existingRequest).build();
  }

  @Override
  @Transactional
  public void remove(Long id) {
    //repository.deleteById(id);
    Optional<Request> o = repository.findById(id);
    if (o.isPresent()) {
      Request request = o.orElseThrow();
      repository.delete(request);
    }
  }

  @Override
  @Transactional
  public List<RequestDto> getRequestsByStatus(RequestStatus status) {
    return repository.findByStatus(status).stream()
            .map(request -> DtoMapperRequest.builder().setRequest(request).build())
            .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public List<RequestDto> getRequestsByUser(Long userId) {
    return repository.findByCitizenId(userId).stream()
            .map(request -> DtoMapperRequest.builder().setRequest(request).build())
            .collect(Collectors.toList());
  }

  //Métodos Aux

  private Document createDocumentFromDto(DocumentDto docDto, Request request) {
    Document doc = new Document();
    doc.setType(docDto.getType());
    doc.setUploadDate(docDto.getUploadDate());
    doc.setFileUrl(docDto.getFileUrl());
    doc.setRequest(request);
    return doc;
  }

  private void updateDocuments(Request request, List<DocumentDto> documentDtos) {
    // Eliminar documentos existentes
    documentRepository.deleteAll(request.getDocuments());
    
    List<Document> newDocuments = documentDtos.stream()
        .map(docDto -> createDocumentFromDto(docDto, request))
        .collect(Collectors.toList());
    
    documentRepository.saveAll(newDocuments);
    request.setDocuments(newDocuments);
  }

}