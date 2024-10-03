package com.azo.backend.msvc.binnacle.msvc_binnacle.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.DocumentDto;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.mapper.DtoMapperDocument;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.entities.Document;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.entities.Request;
import com.azo.backend.msvc.binnacle.msvc_binnacle.repositories.DocumentRepository;
import com.azo.backend.msvc.binnacle.msvc_binnacle.repositories.RequestRepository;

@Service
public class DocumentServiceImpl implements DocumentService {

  @Autowired
  private DocumentRepository repository;

  @Autowired
  private RequestRepository requestRepository;

	@Override
  @Transactional(readOnly = true)
	public List<DocumentDto> findAll() {
		List<Document> documents = (List<Document>) repository.findAll();
    return documents
            .stream()
            .map(doc -> DtoMapperDocument.builder().setDocument(doc).build())
            .collect(Collectors.toList());
	}

  @Override
  @Transactional(readOnly = true)
	public List<DocumentDto> findAllByRequestId(Long requestId) {
		return repository.findAllByRequestId(requestId).stream()
                .map(doc -> DtoMapperDocument.builder().setDocument(doc).build())
                .collect(Collectors.toList());
	}

	@Override
  @Transactional(readOnly = true)
	public Page<DocumentDto> findAllPageByRequestId(Long requestId, Pageable pageable) {
		Page<Document> documents = repository.findAllPageByRequestId(requestId, pageable);
    return documents
              .map(doc -> DtoMapperDocument.builder().setDocument(doc).build());
	}

	@Override
  @Transactional(readOnly = true)
	public Optional<DocumentDto> findById(Long id) {
		return repository.findById(id)
                .map(doc -> DtoMapperDocument.builder().setDocument(doc).build());
	}

	@Override
  @Transactional
	public DocumentDto save(DocumentDto documentDto) {
		// Crear una nueva entidad Document
    Document document = new Document();
    document.setType(documentDto.getType());
    document.setUploadDate(documentDto.getUploadDate());
    document.setFileUrl(documentDto.getFileUrl());

    // Buscar y establecer la relación con Request
    if (documentDto.getRequestId() != null) {
      Request request = requestRepository.findById(documentDto.getRequestId())
              .orElseThrow(() -> new RuntimeException("Request not found"));
      document.setRequest(request);
    } else {
        throw new RuntimeException("RequestId is required");
    }
    
    // Guardar el documento
    Document savedDocument = repository.save(document);
    return DtoMapperDocument.builder().setDocument(savedDocument).build();
	}

	@Override
  @Transactional
	public Optional<DocumentDto> update(DocumentDto documentDto, Long id) {
    return repository.findById(id)
            .map(existingDocument -> {
                // Actualizar los campos del documento existente
                existingDocument.setType(documentDto.getType());
                existingDocument.setFileUrl(documentDto.getFileUrl());

                // Actualizar la relación con Request si es necesario
                if (documentDto.getRequestId() != null) {
                    Request newRequest = requestRepository.findById(documentDto.getRequestId())
                            .orElseThrow(() -> new RuntimeException("Request not found"));
                    existingDocument.setRequest(newRequest);
                }

                // Guardar el documento actualizado
                Document updatedDocument = repository.save(existingDocument);

                // Convertir la entidad actualizada a DTO
                return DtoMapperDocument.builder()
                        .setDocument(updatedDocument)
                        .build();
            });
	}

	@Override
  @Transactional
	public void remove(Long id) {
		repository.deleteById(id);
	}

	@Override
  @Transactional
	public List<DocumentDto> saveAll(List<DocumentDto> documents, Long requestId) {
		Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

    List<Document> documentEntities = documents.stream()
            .map(dto -> {
                Document doc = new Document();
                doc.setType(dto.getType());
                doc.setUploadDate(dto.getUploadDate());
                doc.setFileUrl(dto.getFileUrl());
                doc.setRequest(request);
                return doc;
            })
            .collect(Collectors.toList());

    Iterable<Document> savedDocumentsIterable = repository.saveAll(documentEntities);
    List<Document> savedDocuments = StreamSupport.stream(savedDocumentsIterable.spliterator(), false)
            .collect(Collectors.toList());

    return savedDocuments.stream()
            .map(doc -> DtoMapperDocument.builder().setDocument(doc).build())
            .collect(Collectors.toList());
	}

	@Override
  @Transactional
	public void removeAllByRequestId(Long requestId) {
		List<Document> documents = repository.findAllByRequestId(requestId);
        repository.deleteAll(documents);
	}

}
