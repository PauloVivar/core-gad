package com.azo.backend.msvc.binnacle.msvc_binnacle.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.azo.backend.msvc.binnacle.msvc_binnacle.enums.SubdivisionType;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.SubdivisionCertificateDto;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.validations.ValidationResult;
import com.azo.backend.msvc.binnacle.msvc_binnacle.services.SubdivisionCertificateService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/subdivision-certificates")
@CrossOrigin(originPatterns = "*")
@Tag(name = "CadastralRecordController", description = "Operaciones relacionadas con los certificadfos de pertenercer o no a fraccionamiento")
public class SubdivisionCertificateController {

  private static final Logger logger = LoggerFactory.getLogger(SubdivisionCertificateController.class);

  @Autowired
  private SubdivisionCertificateService service;

  @GetMapping
  public ResponseEntity<List<SubdivisionCertificateDto>> findAll() {
      return ResponseEntity.ok(service.findAll());
  }

  @GetMapping("/page/{page}")
  @Operation(summary = "Obtener certificados de fraccionamiento paginados", description = "Devuelve una lista paginada de certificados de fraccionamiento")
  public ResponseEntity<Page<SubdivisionCertificateDto>> findAll(
          @PathVariable int page,
          @RequestParam(defaultValue = "10") int size) {
      return ResponseEntity.ok(service.findAll(PageRequest.of(page, size)));
  }

  @GetMapping("/{id}")
  @Operation(summary = "Obtener detalle de certificado de fraccionamiento", description = "Devuelve los detalles de un certificado de fraccionamiento por su ID")
  public ResponseEntity<SubdivisionCertificateDto> findById(@PathVariable Long id) {
      return service.findById(id)
              .map(ResponseEntity::ok)
              .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  @Operation(summary = "Registrar nueva solicitud de certificado de fraccionamiento", description = "Crea una nueva solicitud de certificado de fraccionamiento")
  public ResponseEntity<SubdivisionCertificateDto> create(
            @Valid @RequestBody SubdivisionCertificateDto subdivisionCertificateDto) {
      SubdivisionCertificateDto createdCertificate = service.save(subdivisionCertificateDto);
      return ResponseEntity.status(HttpStatus.CREATED).body(createdCertificate);
  }

  @PutMapping("/{id}")
  public ResponseEntity<SubdivisionCertificateDto> update(@RequestBody SubdivisionCertificateDto subdivisionCertificateDto, @PathVariable Long id) {
      return service.update(subdivisionCertificateDto, id)
              .map(ResponseEntity::ok)
              .orElse(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
      service.delete(id);
      return ResponseEntity.noContent().build();
  }

  @GetMapping("/request/{requestId}")
  @Operation(summary = "Obtener certificado de fraccionamiento por ID de solicitud", description = "Devuelve el certificado de fraccionamiento asociado a una solicitud específica")
  public ResponseEntity<SubdivisionCertificateDto> findByRequestId(@PathVariable Long requestId) {
      return service.findByRequestId(requestId)
              .map(ResponseEntity::ok)
              .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping("/{requestId}/validate")
  @Operation(summary = "Validar documentos y estado", description = "Verificar la presencia de los documentos requeridos, el estado de pagos del contribuyente y estado del trámite")
  public ResponseEntity<ValidationResult> validateDocumentsAndStatus(@PathVariable Long requestId) {
      ValidationResult result = service.validateDocumentsAndStatus(requestId);
      return ResponseEntity.ok(result);
  }

  @PostMapping("/{requestId}/generate")
  @Operation(summary = "Generar un certificado de subdivisión", description = "Genera un certificado PDF para una solicitud de subdivisión aprobada.")
  public ResponseEntity<byte[]> generateCertificate(
              @PathVariable Long requestId, 
              @RequestHeader(value = "Authorization", required = false) 
              String authHeader) {
      logger.debug("Generating certificate for requestId: {} with auth header: {}", requestId, authHeader != null ? "present" : "absent");
      try {
          byte[] pdfBytes = service.generateCertificate(requestId);
          HttpHeaders headers = new HttpHeaders();
          headers.setContentType(MediaType.APPLICATION_PDF);
          headers.setContentDispositionFormData("filename", "certificate_" + requestId + ".pdf");
          return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
      } catch (IllegalStateException e) {
          logger.warn("Cannot generate certificate: {}", e.getMessage());
          return ResponseEntity.badRequest().body(e.getMessage().getBytes());
      } catch (Exception e) {
          logger.error("Error generating certificate: ", e);
          return ResponseEntity.internalServerError().body("Error generating certificate".getBytes());
      }
  }

  @PutMapping("/{id}/subdivision-type")
  @Operation(summary = "Actualizar el tipo de subdivisión", description = "Actualiza el tipo de subdivisión que se mostrará en el certificado")
  public ResponseEntity<?> updateSubdivisionType(@PathVariable Long id, @RequestBody SubdivisionType type) {
    try {
        SubdivisionCertificateDto updated = service.updateSubdivisionType(id, type);
        return ResponseEntity.ok(updated);
    } catch (EntityNotFoundException e) {
        return ResponseEntity.notFound().build();
    } catch (Exception e) {
        return ResponseEntity.internalServerError().body("Error updating property belonging type");
    }
  }
  
}
