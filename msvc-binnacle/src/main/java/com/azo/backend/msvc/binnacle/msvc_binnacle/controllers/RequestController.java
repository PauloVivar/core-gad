package com.azo.backend.msvc.binnacle.msvc_binnacle.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.azo.backend.msvc.binnacle.msvc_binnacle.enums.RequestStatus;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.RequestDetailDto;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.RequestDto;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.filter.RequestFilter;
import com.azo.backend.msvc.binnacle.msvc_binnacle.services.RequestService;

import feign.FeignException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

//5. Se crea el ProcedureController

@RestController
@RequestMapping("/api/v1/requests")
@CrossOrigin(originPatterns = "*")
@Tag(name = "RequestController", description = "Operaciones relacionadas con las solicitudes")
public class RequestController {

  @Autowired
  private RequestService service;

  @GetMapping
  @Operation(summary = "Obtener solicitudes", description = "Devuelve una lista de todas las solicitudes registradas")
  public ResponseEntity<List<RequestDto>> list () {
    return ResponseEntity.ok(service.findAll());
  }

  @GetMapping("/page/{page}")
  @Operation(summary = "Obtener solicitudes paginadas", description = "Devuelve una lista paginada de solicitudes")
  public Page<RequestDto> list(@PathVariable Integer page){
    Pageable pageable = PageRequest.of(page, 5);
    return service.findAll(pageable);
  }

  @GetMapping("/page")
  public ResponseEntity<Page<RequestDto>> listRequestsPaginated(
          RequestFilter filter,
          @RequestParam(defaultValue = "0") int page,
          @RequestParam(defaultValue = "10") int size) {
      Pageable pageable = PageRequest.of(page, size);
      Page<RequestDto> requests = service.findAllWithFilters(filter, pageable);
      return ResponseEntity.ok(requests);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Obtener detalle solicitud", description = "Devuelve los detalles de una solicitud por su ID")
  public ResponseEntity<?> detail(
    @Parameter(description = "ID de la solicitud", required = true) 
    @PathVariable Long id) {
      Optional<RequestDetailDto> o = service.findById(id);
      if(o.isPresent()) {
        return ResponseEntity.ok(o.orElseThrow());
      }
      return ResponseEntity.notFound().build();
  }

  @PostMapping
  @Operation(summary = "Crear nueva solicitud", description = "Crea una nueva solicitud a partir de los datos proporcionados")
  public ResponseEntity<?> create (
          @Valid @RequestBody RequestDto requestDto, 
            BindingResult result) {
    if(result.hasErrors()){
      return validate(result);
    }

    try {
      RequestDto createdRequest = service.save(requestDto);
      return ResponseEntity.status(HttpStatus.CREATED).body(createdRequest);
    } catch (EntityNotFoundException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("Error al crear la solicitud: " + e.getMessage());
  }
    //return ResponseEntity.status(HttpStatus.CREATED).body(service.save(request));
  }

  @PutMapping("/{id}")
  @Operation(summary = "Actualizar solicitud", description = "Actualiza una solicitud existente con nuevos datos")
  public ResponseEntity<?> update(
          @Valid @RequestBody RequestDto requestDto,
            BindingResult result,
          @Parameter(description = "ID de la solicitud", required = true)  
          @PathVariable Long id) {
    if(result.hasErrors()){
      return validate(result);
    }

    try {
      RequestDto updatedRequest = service.update(id, requestDto);
      return ResponseEntity.ok(updatedRequest);
    } catch (Exception e) {
        return ResponseEntity.notFound().build();
    }
    //return ResponseEntity.status(HttpStatus.CREATED).body(service.update(id, request));
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Eliminar solicitud", description = "Elimina una solicitud existente")
  public ResponseEntity<?> remove ( 
          @Parameter(description = "ID de la solicitud", required = true)
          @PathVariable Long id){
    Optional<RequestDetailDto> o = service.findById(id);
    if(o.isPresent()){
      service.remove(id);
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.notFound().build();
  }

  @GetMapping("/status/{status}")
  @Operation(summary = "Obtener solicitudes por estado", description = "Devuelve una lista de solicitudes filtradas por su estado")
  public ResponseEntity<?> getRequestsByStatus(
          @Parameter(description = "Estado solicitud", required = true)
          @PathVariable RequestStatus status) {
    try {
      List<RequestDto> requests = service.getRequestsByStatus(status);
      if (requests.isEmpty()) {
          return ResponseEntity.noContent().build();
      }
      return ResponseEntity.ok(requests);
    } catch (IllegalArgumentException e) {
        // Esta excepción se lanzaría si el estado proporcionado no es válido
        return ResponseEntity
            .badRequest()
            .body(Collections.singletonMap("error", "Estado de solicitud inválido: " + e.getMessage()));
    } catch (Exception e) {
        // Captura cualquier otra excepción no esperada
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(Collections.singletonMap("error", "Error interno del servidor: " + e.getMessage()));
    }
    //return ResponseEntity.ok(service.getRequestsByStatus(status));
  }

  @GetMapping("/user/{userId}")
  @Operation(summary = "Obtener solicitudes por usuario", description = "Devuelve una lista de solicitudes filtradas por usuario")
  public ResponseEntity<?> getRequestsByUser(
          @Parameter(description = "ID del usuario", required = true)
          @PathVariable Long userId) {
    try {
      List<RequestDto> requests = service.getRequestsByUser(userId);
      return ResponseEntity.ok(requests);
    } catch (FeignException e) {
        // Manejo específico para errores de comunicación con el servicio de usuarios
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
            .body(Collections.singletonMap("error", "Error al comunicarse con el servicio de usuarios: " + e.getMessage()));
    } catch (Exception e) {
        // Manejo general de otras excepciones
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(Collections.singletonMap("error", "Error interno del servidor: " + e.getMessage()));
    }
    //return ResponseEntity.ok(service.getRequestsByUser(userId));
  }

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
