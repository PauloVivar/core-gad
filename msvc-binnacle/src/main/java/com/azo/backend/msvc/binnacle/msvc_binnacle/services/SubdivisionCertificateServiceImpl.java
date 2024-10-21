package com.azo.backend.msvc.binnacle.msvc_binnacle.services;

import java.util.List;
import java.util.Optional;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;
import java.util.stream.Collectors;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.azo.backend.msvc.binnacle.msvc_binnacle.enums.DocumentType;
import com.azo.backend.msvc.binnacle.msvc_binnacle.enums.RequestStatus;
import com.azo.backend.msvc.binnacle.msvc_binnacle.enums.SubdivisionType;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.RequestDetailDto;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.SubdivisionCertificateDto;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.mapper.DtoMapperSubdivisionCertificate;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.entities.Document;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.entities.Request;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.entities.SubdivisionCertificate;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.validations.ValidationResult;
import com.azo.backend.msvc.binnacle.msvc_binnacle.repositories.DocumentRepository;
import com.azo.backend.msvc.binnacle.msvc_binnacle.repositories.RequestRepository;
import com.azo.backend.msvc.binnacle.msvc_binnacle.repositories.SubdivisionCertificateRepository;

import jakarta.persistence.EntityNotFoundException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class SubdivisionCertificateServiceImpl implements SubdivisionCertificateService {

  private static final Logger logger = LoggerFactory.getLogger(SubdivisionCertificateServiceImpl.class);

  @Autowired
  private SubdivisionCertificateRepository repository;

  @Autowired
  private RequestRepository requestRepository;

  @Autowired
  private DocumentRepository documentRepository;

  @Autowired
  private PaymentValidationService paymentValidationService;

  @Autowired
  private RequestService requestService;

  @Override
  @Transactional(readOnly = true)
  public List<SubdivisionCertificateDto> findAll() {
    List<SubdivisionCertificate> subdivision = (List<SubdivisionCertificate>) repository.findAll();
    return subdivision.stream()
            .map(s -> DtoMapperSubdivisionCertificate.builder().setSubdivisionCertificate(s).build())
            .collect(Collectors.toList());
  }

  @Override
  @Transactional(readOnly = true)
  public Page<SubdivisionCertificateDto> findAll(Pageable pageable) {
    Page<SubdivisionCertificate> cadastralRecords = repository.findAll(pageable);
    return cadastralRecords
              .map(s -> DtoMapperSubdivisionCertificate.builder().setSubdivisionCertificate(s).build());
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<SubdivisionCertificateDto> findById(Long id) {
    return repository.findById(id)
            .map(s -> DtoMapperSubdivisionCertificate
                        .builder()
                        .setSubdivisionCertificate(s)
                        .build());
  }

  @Override
  @Transactional
  public SubdivisionCertificateDto save(SubdivisionCertificateDto subdivisionCertificateDto) {
    Request request = requestRepository.findById(subdivisionCertificateDto.getRequestId())
                .orElseThrow(() -> new EntityNotFoundException("Solicitu no encontrada"));
    
    if (request.getStatus() != RequestStatus.INGRESADO) {
        throw new IllegalStateException("No se puede crear un certificado para una solicitud que no está en estado INGRESADO");
    }

    SubdivisionCertificate certificate = DtoMapperSubdivisionCertificate.toEntity(subdivisionCertificateDto);
    certificate.setRequest(request);
    certificate.setIssueDate(LocalDate.now());
    certificate = repository.save(certificate);
    return DtoMapperSubdivisionCertificate.build(certificate);
  }

  @Override
  @Transactional
  public Optional<SubdivisionCertificateDto> update(SubdivisionCertificateDto subdivisionCertificateDto, Long id) {
    return repository.findById(id)
                .map(certificate -> {
                    SubdivisionCertificate updatedCertificate = DtoMapperSubdivisionCertificate.toEntity(subdivisionCertificateDto);
                    updatedCertificate.setId(id);
                    updatedCertificate.setRequest(certificate.getRequest());
                    return DtoMapperSubdivisionCertificate.build(repository.save(updatedCertificate));
                });
  }

  @Override
  @Transactional
  public void delete(Long id) {
    repository.deleteById(id);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<SubdivisionCertificateDto> findByRequestId(Long requestId) {
    return repository.findByRequestId(requestId)
                .map(s -> DtoMapperSubdivisionCertificate
                            .builder()
                            .setSubdivisionCertificate(s)
                            .build());
  }

  @Transactional(readOnly = true)
  public ValidationResult validateDocumentsAndStatus(Long requestId) {
      SubdivisionCertificate certificate = repository.findByRequestId(requestId)
              .orElseThrow(() -> new EntityNotFoundException("Certificado no encontrado para la solicitud: " + requestId));

      boolean documentsValid = validateDocuments(certificate.getRequest().getId());
      boolean paymentsValid = validatePayments(certificate.getRequest().getCitizenId());
      boolean statusValid = validateStatus(certificate);

      if (!statusValid) {
        logger.warn("La solicitud {} no está en estado APROBADO. Estado actual: {}", 
                    requestId, certificate.getRequest().getStatus());
      }

      boolean isValid = documentsValid && paymentsValid && statusValid;

      // return new ValidationResult(documentsValid, paymentsValid, statusValid);
      return new ValidationResult(documentsValid, paymentsValid, statusValid, isValid);
  }
  
  //metodos utils
  private boolean validateDocuments(Long requestId) {
      List<Document> documents = documentRepository.findAllByRequestId(requestId);
      // Verificar que los documentos necesarios estén presentes
      boolean hasEscritura = documents.stream().anyMatch(d -> d.getType() == DocumentType.ESCRITURA);
      boolean hasCertificadoNoAdeudar = documents.stream().anyMatch(d -> d.getType() == DocumentType.CERTIFICADO_NO_ADEUDAR);
      return hasEscritura && hasCertificadoNoAdeudar;
  }

  private boolean validatePayments(Long citizenId) {
      // Aquí llamamos al servicio de validación de pagos
      return paymentValidationService.validatePaymentStatus(citizenId.toString());
  }

  private boolean validateStatus(SubdivisionCertificate certificate) {
      // Verificar que el estado del trámite permita avanzar
      return certificate.getRequest().getStatus() == RequestStatus.APROBADO;
  }

  @Override
  @Transactional
  public byte[] generateCertificate(Long requestId) throws Exception {
      SubdivisionCertificate certificate = repository.findByRequestId(requestId)
          .orElseThrow(() -> new EntityNotFoundException("Certificado no encontrado para la solicitud: " + requestId));

      RequestDetailDto requestDetail = requestService.findById(requestId)
          .orElseThrow(() -> new EntityNotFoundException("Detalle de solicitud no encontrado para ID: " + requestId));

      if (requestDetail.getStatus() != RequestStatus.APROBADO) {
          throw new IllegalStateException("No se puede generar el certificado. El estado de la solicitud no es APROBADO.");
      }

      Map<String, Object> parameters = new HashMap<>();
      parameters.put("requestId", requestId);
      parameters.put("citizenName", requestDetail.getCitizenName());
      parameters.put("cadastralCode", requestDetail.getCadastralCode());
      parameters.put("propertyBelongingType", certificate.getSubdivisionType().getDescription());
      parameters.put("issueDate", certificate.getIssueDate().format(DateTimeFormatter.ofPattern("dd 'de' MMMM 'del' yyyy")));

      // Añade más parámetros según sea necesario

      JasperPrint jasperPrint = JasperFillManager.fillReport(
          getClass().getResourceAsStream("/reports/subdivision_certificate.jasper"),
          parameters,
          new JRBeanCollectionDataSource(Collections.singletonList(certificate))
      );

      byte[] pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);

      // Actualizar el estado y la URL del archivo
      certificate.setFileUrl("/certificates/" + requestId + ".pdf");
      certificate.setIssueDate(LocalDate.now());
      repository.save(certificate);

      logger.info("Certificate generated successfully for request id: {}", requestId);
      return pdfBytes;
  }

  @Override
  @Transactional
  public SubdivisionCertificateDto updateSubdivisionType(Long id, SubdivisionType type) throws EntityNotFoundException {
    SubdivisionCertificate certificate = repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Certificate not found with id: " + id));
        certificate.setSubdivisionType(type);
        certificate = repository.save(certificate);
        return DtoMapperSubdivisionCertificate.build(certificate);
        //return convertToDto(certificate);
  }

}
