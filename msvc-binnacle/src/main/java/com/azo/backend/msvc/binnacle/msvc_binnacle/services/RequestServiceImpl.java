package com.azo.backend.msvc.binnacle.msvc_binnacle.services;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.azo.backend.msvc.binnacle.msvc_binnacle.clients.UserClientRest;
import com.azo.backend.msvc.binnacle.msvc_binnacle.enums.RequestStatus;
import com.azo.backend.msvc.binnacle.msvc_binnacle.factories.RequestFactory;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.User;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.CorrectionDto;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.DocumentDto;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.RequestDetailDto;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.RequestDto;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.TechnicalReviewDto;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.mapper.DtoMapperDocument;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.mapper.DtoMapperRequest;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.entities.CadastralRecord;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.entities.Document;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.entities.Request;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.entities.RequestCadastralRecord;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.entities.RequestSubdivisionCertificate;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.filter.RequestFilter;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.filter.RequestSpecification;
import com.azo.backend.msvc.binnacle.msvc_binnacle.repositories.CadastralRecordRepository;
import com.azo.backend.msvc.binnacle.msvc_binnacle.repositories.RequestRepository;

import feign.FeignException;
import jakarta.persistence.EntityNotFoundException;

//paso 4.1.
@Service
public class RequestServiceImpl implements RequestService {

  private static final Logger logger = LoggerFactory.getLogger(RequestServiceImpl.class);

  @Autowired
  private RequestRepository repository;

  @Autowired
  private CadastralRecordRepository cadastralRecordRepository;

  @Autowired
  private RequestFactory requestFactory;

  @Autowired
  private DocumentService documentService;

  @Autowired
  private TechnicalReviewService technicalReviewService;

  @Autowired
  private CorrectionService correctionService;

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
    Page<Request> requests = repository.findAll(pageable);
    return requests.map(r -> DtoMapperRequest.builder().setRequest(r).build());
  }

  public Page<RequestDto> findAllWithFilters(RequestFilter filter, Pageable pageable) {
    // Usa la especificación con el filtro
    Page<Request> requests = repository.findAll(RequestSpecification.withFilter(filter), pageable);
    // Mapea las entidades Request a DTOs
    return requests.map(r -> DtoMapperRequest.builder().setRequest(r).build());
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<RequestDetailDto> findById(Long id) {
    return repository.findById(id).map(r -> {
      try {
        User citizen = userClientRest.detail(r.getCitizenId());
        // User assignedUser = userClientRest.detail(r.getAssignedToUserId());
        User assignedUser = null;
        if (r.getAssignedToUserId() != null) {
          assignedUser = userClientRest.detail(r.getAssignedToUserId());
        }
        return DtoMapperRequest.builder()
                .setRequest(r)
                .setCitizen(citizen)
                .setAssignedUser(assignedUser)
                .buildDetailDto();
      } catch(FeignException e){
        logger.error("Error calling user service: {}", e.getMessage());
        logger.error("Status: {}, Content: {}", e.status(), e.contentUTF8());
        // return un DTO parcial o lanzar una excepción personalizada
        throw new RuntimeException("Error retrieving user details", e);
      }
    });
    //return repository.findById(id);
  }

  @Override
  @Transactional
  public RequestDto save(RequestDto requestDto) {

    // Verificar si el cadastralCode existe
    CadastralRecord cadastralRecord = cadastralRecordRepository.findById(requestDto.getCadastralCode())
        .orElseThrow(() -> new EntityNotFoundException("No se encontró registro catastral con código: " + requestDto.getCadastralCode()));

    //Request request = new Request();
    Request request = requestFactory.createRequest(requestDto.getType());
    request.setEntryDate(requestDto.getEntryDate());
    request.setStatus(requestDto.getStatus());
    request.setType(requestDto.getType());
    request.setCitizenId(requestDto.getCitizenId());
    request.setCadastralRecord(cadastralRecord);
    //request.setAssignedToUserId(requestDto.getAssignedToUserId());

    // Guarda primero para obtener el ID
    request = repository.save(request);
    // Llamar al método process() específico del tipo de solicitud
    request.process();
    // Guarda nuevamente para persistir los cambios
    request = repository.save(request);

    // Manejar documentos
    if (requestDto.getDocuments() != null && !requestDto.getDocuments().isEmpty()) {
      List<DocumentDto> savedDocuments = documentService.saveAll(requestDto.getDocuments(), request.getId());
      final Request finalRequest = request;  // Crear una referencia final
      List<Document> documents = savedDocuments.stream()
              .map(dto -> DtoMapperDocument.toEntity(dto, finalRequest))
              .collect(Collectors.toList());
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
    
    // Actualizar CadastralRecord si ha cambiado
    if (!existingRequest.getCadastralRecord().getCadastralCode().equals(requestDto.getCadastralCode())) {
      CadastralRecord newCadastralRecord = cadastralRecordRepository.findById(requestDto.getCadastralCode())
          .orElseThrow(() -> new EntityNotFoundException("CadastralRecord not found with code: " + requestDto.getCadastralCode()));
      existingRequest.setCadastralRecord(newCadastralRecord);
    }

    // Actualizar assignedToUserId si está presente en el DTO
    if (requestDto.getAssignedToUserId() != null) {
        existingRequest.setAssignedToUserId(requestDto.getAssignedToUserId());
    }
    
    // Actualizar documentos
    if (requestDto.getDocuments() != null) {
      documentService.removeAllByRequestId(id);
      List<DocumentDto> updatedDocuments = documentService.saveAll(requestDto.getDocuments(), id);
      final Request finalRequest = existingRequest;  // Crear una referencia final
      List<Document> documents = updatedDocuments.stream()
              .map(dto -> DtoMapperDocument.toEntity(dto, finalRequest))
              .collect(Collectors.toList());
      existingRequest.setDocuments(documents);
    }

    // Manejar actualizaciones específicas según el tipo de solicitud
    if (existingRequest instanceof RequestCadastralRecord) {
      updateTechnicalReviewsAndCorrections(requestDto, id);
    } else if (existingRequest instanceof RequestSubdivisionCertificate) {
        // Aquí para manejar actualizaciones específicas para RequestSubdivisionCertificate aun por implementar
    }

    existingRequest = repository.save(existingRequest);
    return DtoMapperRequest.builder().setRequest(existingRequest).build();
  }

  @Override
  @Transactional
  public void remove(Long id) {
    try {
      Optional<Request> o = repository.findById(id);
      if (o.isPresent()) {
          Request request = o.orElseThrow();
          // La eliminación en cascada se encargará de los documentos
          // Fuerza la carga de todas las relaciones
          request.getDocuments().size();
          request.getTechnicalReviews().size();
          request.getCorrections().size();
          if (request instanceof RequestSubdivisionCertificate) {
              ((RequestSubdivisionCertificate) request).getSubdivisionCertificate();
          }
          repository.delete(request);
      } else {
          throw new EntityNotFoundException("Solicitud no encontrada con ID: " + id);
      }
    } catch (Exception e) {
        // Log the exception
        logger.error("Error al eliminar la solicitud con ID: " + id, e);
        throw new RuntimeException("No se pudo eliminar la solicitud", e);
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

  private void updateTechnicalReviewsAndCorrections(RequestDto requestDto, Long requestId) {
    // Actualizar revisiones técnicas
    if (requestDto.getTechnicalReviews() != null) {
      for (TechnicalReviewDto reviewDto : requestDto.getTechnicalReviews()) {
        if (reviewDto.getId() == null) {
            // Nueva revisión técnica
            reviewDto.setRequestId(requestId);
            technicalReviewService.save(reviewDto);
        } else {
            // Actualizar revisión técnica existente
            technicalReviewService.update(reviewDto, reviewDto.getId());
        }
      }
    }

    // Actualizar correcciones
    if (requestDto.getCorrections() != null) {
      for (CorrectionDto correctionDto : requestDto.getCorrections()) {
        if (correctionDto.getId() == null) {
            // Nueva corrección
            correctionDto.setRequestId(requestId);
            correctionService.save(correctionDto);
        } else {
            // Actualizar corrección existente
            correctionService.update(correctionDto, correctionDto.getId());
        }
      }
    }
  }

}
