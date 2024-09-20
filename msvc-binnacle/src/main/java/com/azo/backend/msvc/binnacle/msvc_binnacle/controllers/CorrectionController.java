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

import com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.CorrectionDto;
import com.azo.backend.msvc.binnacle.msvc_binnacle.services.CorrectionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/requests/{requestId}/corrections")
@Tag(name = "CorrectionController", description = "Operaciones relacionadas con las subsanaciones")
public class CorrectionController {
  @Autowired
  private CorrectionService service;

  @GetMapping
  @Operation(summary = "Obtener subsanaciones", description = "Devuelve una lista de todas las subsanaciones registradas")
  public ResponseEntity<List<CorrectionDto>> list(@PathVariable Long requestId) {
      return ResponseEntity.ok(service.findByRequestId(requestId));
  }

  @GetMapping("/page/{page}")
  @Operation(summary = "Obtener subsanaciones paginadas", description = "Devuelve una lista paginada de subsanaciones")
  public ResponseEntity<Page<CorrectionDto>> list(
    @PathVariable Long requestId, 
    @PathVariable Integer page) {
      Pageable pageable = PageRequest.of(page, 5);
      Page<CorrectionDto> corrections = service.findAllByRequestId(requestId, pageable);
      return ResponseEntity.ok(corrections);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Obtener detalle subsanación", description = "Devuelve los detalles de una subsanación por su ID")
  public ResponseEntity<?> detail(@PathVariable Long requestId, @PathVariable Long id) {
      Optional<CorrectionDto> o = service.findById(id);
      if (o.isPresent()) {
          return ResponseEntity.ok(o.get());
      }
      return ResponseEntity.notFound().build();
  }

  @PostMapping
  @Operation(summary = "Crear nueva subsanación", description = "Crea una nueva subsanación a partir de los datos proporcionados")
  public ResponseEntity<?> create(
    @PathVariable Long requestId, 
    @RequestBody CorrectionDto correctionDto) {
      correctionDto.setRequestId(requestId);
      CorrectionDto savedCorrection = service.save(correctionDto);
      return ResponseEntity.status(HttpStatus.CREATED).body(savedCorrection);
  }

  @PutMapping("/{id}")
  @Operation(summary = "Actualizar subsanación", description = "Actualiza una subsanación existente con nuevos datos")
  public ResponseEntity<?> update(
    @PathVariable Long requestId, 
    @RequestBody CorrectionDto correctionDto, 
    @PathVariable Long id) {
      correctionDto.setRequestId(requestId);
      Optional<CorrectionDto> o = service.update(correctionDto, id);
      if (o.isPresent()) {
          return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
      }
      return ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Eliminar subsanación", description = "Elimina una subsanación existente")
  public ResponseEntity<?> remove(@PathVariable Long requestId, @PathVariable Long id) {
      Optional<CorrectionDto> o = service.findById(id);
      if (o.isPresent()) {
          service.remove(id);
          return ResponseEntity.noContent().build();
      }
      return ResponseEntity.notFound().build();
  }

  // @GetMapping("/request/{requestId}")
  // @Operation(summary = "Buscar por Solicitud", description = "Busca subsanaciones por una solicitud existente")
  // public ResponseEntity<List<CorrectionDto>> findByRequestId(@PathVariable Long requestId) {
  //     List<CorrectionDto> corrections = service.findByRequestId(requestId);
  //     return ResponseEntity.ok(corrections);
  // }
  
}
