package com.azo.backend.msvc.binnacle.msvc_binnacle.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/reviews")
@Tag(name = "TechnicalReviewController", description = "Operaciones relacionadas con las revisiones_técnicas")
public class TechnicalReviewController {
  @Autowired
  private TechnicalReviewService service;

  @GetMapping
  @Operation(summary = "Obtener revisiones_técnicas", description = "Devuelve una lista de todas las revisiones_técnicas registradas")
  public ResponseEntity<List<TechnicalReviewDto>> list() {
    return ResponseEntity.ok(service.findAll());
  }

  @GetMapping("/page/{page}")
  @Operation(summary = "Obtener revisiones_técnicas paginadas", description = "Devuelve una lista paginada de revisiones_técnicas")
  public Page<TechnicalReviewDto> list(@PathVariable Integer page){
    Pageable pageable = PageRequest.of(page, 5);
    return service.findAll(pageable);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Obtener detalle revisión_técnica", description = "Devuelve los detalles de una revisión_técnica por su ID")
  public ResponseEntity<?> detail(@PathVariable Long id) {
    Optional<TechnicalReviewDto> o = service.findById(id);
    if (o.isPresent()) {
        return ResponseEntity.ok(o.get());
    }
    return ResponseEntity.notFound().build();
  }

  @PostMapping
  @Operation(summary = "Crear nueva revisión_técnica", description = "Crea una nueva revisión_técnica a partir de los datos proporcionados")
  public ResponseEntity<?> create(@RequestBody TechnicalReviewDto review) {
    TechnicalReviewDto reviewDb = service.save(review);
    return ResponseEntity.status(HttpStatus.CREATED).body(reviewDb);
  }

  @PutMapping("/{id}")
  @Operation(summary = "Actualizar revisión_técnica", description = "Actualiza una revisión_técnica existente con nuevos datos")
  public ResponseEntity<?> update(@RequestBody TechnicalReviewDto review, @PathVariable Long id) {
    Optional<TechnicalReviewDto> o = service.update(review, id);
    if (o.isPresent()) {
        return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
    }
    return ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Eliminar revisión_técnica", description = "Elimina una revisión_técnica existente")
  public ResponseEntity<?> remove(@PathVariable Long id) {
    Optional<TechnicalReviewDto> o = service.findById(id);
    if (o.isPresent()) {
        service.remove(id);
        return ResponseEntity.noContent().build();
    }
    return ResponseEntity.notFound().build();
  }

  @GetMapping("/request/{requestId}")
  @Operation(summary = "Buscar por Solicitud", description = "Busca revisiones_técnicas por una solicitud existente")
  public ResponseEntity<List<TechnicalReviewDto>> findByRequestId(@PathVariable Long requestId) {
    List<TechnicalReviewDto> reviews = service.findByRequestId(requestId);
    return ResponseEntity.ok(reviews);
  }
  
}
