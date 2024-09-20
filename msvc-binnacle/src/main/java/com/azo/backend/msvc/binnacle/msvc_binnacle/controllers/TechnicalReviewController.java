package com.azo.backend.msvc.binnacle.msvc_binnacle.controllers;

import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.TechnicalReviewDto;
import com.azo.backend.msvc.binnacle.msvc_binnacle.services.TechnicalReviewService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/requests/{requestId}/reviews")
@Tag(name = "TechnicalReviewController", description = "Operaciones relacionadas con las revisiones técnicas")
public class TechnicalReviewController {
  @Autowired
  private TechnicalReviewService service;

  @GetMapping
  @Operation(summary = "Obtener revisiones técnicas", description = "Devuelve una lista de todas las revisiones técnicas registradas")
  public ResponseEntity<List<TechnicalReviewDto>> list(@PathVariable Long requestId) {
    return ResponseEntity.ok(service.findByRequestId(requestId));
    //return ResponseEntity.ok(service.findAll());
  }

  @GetMapping("/page/{page}")
  @Operation(summary = "Obtener revisiones técnicas paginadas", description = "Devuelve una lista paginada de revisiones técnicas")
  public ResponseEntity<Page<TechnicalReviewDto>> list(
          @Parameter(description = "ID de la solicitud", required = true)   
          @PathVariable Long requestId, 
          @Parameter(description = "Número de la página", required = true) 
          @PathVariable Integer page){
    Pageable pageable = PageRequest.of(page, 5);
    Page<TechnicalReviewDto> reviews = service.findAllByRequestId(requestId, pageable);
    return ResponseEntity.ok(reviews);
    //return service.findAll(pageable);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Obtener detalle revisión técnica", description = "Devuelve los detalles de una revisión técnica por su ID")
  public ResponseEntity<?> detail(
          @Parameter(description = "ID de la solicitud", required = true)
          @PathVariable Long requestId, 
          @Parameter(description = "ID de la revisión técnica", required = true)
          @PathVariable Long id) {
    Optional<TechnicalReviewDto> o = service.findById(id);
    if (o.isPresent()) {
        return ResponseEntity.ok(o.get());
    }
    return ResponseEntity.notFound().build();
  }

  @PostMapping
  @Operation(summary = "Crear nueva revisión técnica", description = "Crea una nueva revisión técnica a partir de los datos proporcionados")
  public ResponseEntity<?> create(
          @Parameter(description = "ID de la solicitud", required = true)  
          @PathVariable Long requestId, 
          @Valid @RequestBody TechnicalReviewDto reviewDto,
            BindingResult result) {
      if(result.hasErrors()){
        return validate(result);
      }

      reviewDto.setRequestId(requestId);
      TechnicalReviewDto savedReview = service.save(reviewDto);
      return ResponseEntity.status(HttpStatus.CREATED).body(savedReview);
  }

  @PutMapping("/{id}")
  @Operation(summary = "Actualizar revisión técnica", description = "Actualiza una revisión técnica existente con nuevos datos")
  public ResponseEntity<?> update(
          @Parameter(description = "ID de la solicitud", required = true)
          @PathVariable Long requestId, 
          @Valid @RequestBody TechnicalReviewDto reviewDto,
            BindingResult result,
          @Parameter(description = "ID de la revisión técnica", required = true) 
          @PathVariable Long id) {
      if(result.hasErrors()){
        return validate(result);
      }

      reviewDto.setRequestId(requestId);
      Optional<TechnicalReviewDto> o = service.update(reviewDto, id);
      if (o.isPresent()) {
          return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
      }
      return ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Eliminar revisión técnica", description = "Elimina una revisión técnica existente")
  public ResponseEntity<?> remove(
          @Parameter(description = "ID de la solicitud", required = true)  
          @PathVariable Long requestId, 
          @Parameter(description = "ID de la revisión técnica", required = true)
          @PathVariable Long id) {
    Optional<TechnicalReviewDto> o = service.findById(id);
    if (o.isPresent()) {
        service.remove(id);
        return ResponseEntity.noContent().build();
    }
    return ResponseEntity.notFound().build();
  }

  // @GetMapping("/request/{requestId}")
  // @Operation(summary = "Buscar por Solicitud", description = "Busca revisiones técnicas por una solicitud existente")
  // public ResponseEntity<List<TechnicalReviewDto>> findByRequestId(@PathVariable Long requestId) {
  //   List<TechnicalReviewDto> reviews = service.findByRequestId(requestId);
  //   return ResponseEntity.ok(reviews);
  // }

  //METODOS AUXILIARES
  //metodos utilitario para validar que un error si se repite el user o email
  private ResponseEntity<Map<String, String>> validate (BindingResult result){
    Map<String, String> errors = new HashMap<>();
    result.getFieldErrors().forEach( err -> {
      errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
    });
    return ResponseEntity.badRequest().body(errors);
  }
  
}
