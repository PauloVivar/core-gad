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
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.RequestDto;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.mapper.DtoMapperDocument;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.mapper.DtoMapperRequest;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.entities.Request;
import com.azo.backend.msvc.binnacle.msvc_binnacle.repositories.RequestRepository;

//paso 4.1.
@Service
public class RequestServiceImpl implements RequestService {

  @Autowired
  private RequestRepository repository;

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
  public Optional<RequestDto> findById(Long id) {
    return repository.findById(id).map(r -> enrichRequestDTO(DtoMapperRequest
      .builder()
      .setRequest(r)
      .build()));
    //return repository.findById(id);
  }

  @Override
  @Transactional
  public RequestDto save(Request request) {
    request = repository.save(request);
    return enrichRequestDTO(DtoMapperRequest.builder().setRequest(request).build());
    // return DtoMapperRequest.builder().setRequest(repository.save(request)).build();
  }

  @Override
  @Transactional
  public RequestDto update(Long id, Request request) {
    Request existingRequest = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found"));
        
        // Actualizar los campos necesarios
        existingRequest.setStatus(request.getStatus());
        existingRequest.setCadastralCode(request.getCadastralCode());
        existingRequest.setAssignedToUserId(request.getAssignedToUserId());
        // ... actualizar otros campos según sea necesario

        existingRequest = repository.save(existingRequest);
        return enrichRequestDTO(DtoMapperRequest.builder().setRequest(existingRequest).build());
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
      .map(request -> enrichRequestDTO(DtoMapperRequest.builder().setRequest(request).build()))
      .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public List<RequestDto> getRequestsByUser(Long userId) {
    return repository.findByCitizenId(userId).stream()
      .map(request -> enrichRequestDTO(DtoMapperRequest.builder().setRequest(request).build()))
      .collect(Collectors.toList());
  }

  //metodos aux

  private RequestDto enrichRequestDTO(RequestDto dto) {
    // Obtener información del usuario desde msvc-users
    try {
        User user = userClientRest.detail(dto.getCitizenId());
        dto.setCitizenName(user.getUsername());
        dto.setCitizenEmail(user.getEmail());
    } catch (Exception e) {
        // Manejar el error si no se puede obtener la información del usuario
    }
    
    // Obtener y mapear los documentos si es necesario
    Request request = repository.findById(dto.getId()).orElse(null);
    if (request != null && request.getDocuments() != null) {
        dto.setDocuments(request.getDocuments().stream()
                .map(doc -> DtoMapperDocument.builder().setDocument(doc).build())
                .collect(Collectors.toList()));
    }
    
    return dto;
  }

}
