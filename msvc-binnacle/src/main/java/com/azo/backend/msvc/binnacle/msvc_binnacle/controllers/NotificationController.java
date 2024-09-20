package com.azo.backend.msvc.binnacle.msvc_binnacle.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

import com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.NotificationDto;
import com.azo.backend.msvc.binnacle.msvc_binnacle.services.NotificationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/requests/{requestId}/notifications")
@Tag(name = "NotificationController", description = "Operaciones relacionadas con las notificaciones")
public class NotificationController {
  @Autowired
  private NotificationService service;

  @GetMapping
  @Operation(summary = "Obtener notificaciones", description = "Devuelve una lista de todas las notificaciones registradas")
  public ResponseEntity<List<NotificationDto>> list(@PathVariable Long requestId) {
    return ResponseEntity.ok(service.findByRequestId(requestId));
  }

  @GetMapping("/page/{page}")
  @Operation(summary = "Obtener notificaciones paginadas", description = "Devuelve una lista paginada de notificaciones")
  public ResponseEntity<Page<NotificationDto>> list(@PathVariable Long requestId, 
                                                    @PathVariable Integer page) {
    Pageable pageable = PageRequest.of(page, 5);
    Page<NotificationDto> notifications = service.findAllByRequestId(requestId, pageable);
    return ResponseEntity.ok(notifications);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Obtener detalle notificación", description = "Devuelve los detalles de una notificación por su ID")
  public ResponseEntity<?> detail(@PathVariable Long requestId, @PathVariable UUID id) {
    Optional<NotificationDto> o = service.findById(id);
    if (o.isPresent()) {
        return ResponseEntity.ok(o.get());
    }
    return ResponseEntity.notFound().build();
  }

  @PostMapping
  @Operation(summary = "Crear nueva notificación", description = "Crea una nueva notificación a partir de los datos proporcionados")
  public ResponseEntity<?> create(@PathVariable Long requestId, 
                                  @RequestBody NotificationDto notificationDto) {
    notificationDto.setRequestId(requestId);
    NotificationDto savedNotification = service.save(notificationDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(savedNotification);
  }

  @PutMapping("/{id}")
  @Operation(summary = "Actualizar notificación", description = "Actualiza una notificación existente con nuevos datos")
  public ResponseEntity<?> update(
    @PathVariable Long requestId, 
    @RequestBody NotificationDto notificationDto, 
    @PathVariable UUID id) {
      notificationDto.setRequestId(requestId);
      Optional<NotificationDto> o = service.update(notificationDto, id);
      if (o.isPresent()) {
          return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
      }
      return ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Eliminar notificación", description = "Elimina una notificación existente")
  public ResponseEntity<?> remove(@PathVariable Long requestId, @PathVariable UUID id) {
    Optional<NotificationDto> o = service.findById(id);
    if (o.isPresent()) {
        service.remove(id);
        return ResponseEntity.noContent().build();
    }
    return ResponseEntity.notFound().build();
  }

  // @GetMapping("/request/{requestId}")
  // @Operation(summary = "Buscar por Solicitud", description = "Busca notificaciones por una solicitud existente")
  // public ResponseEntity<List<NotificationDto>> findByRequestId(@PathVariable Long requestId) {
  //     List<NotificationDto> notifications = service.findByRequestId(requestId);
  //     return ResponseEntity.ok(notifications);
  // }
  
}
