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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.azo.backend.msvc.binnacle.msvc_binnacle.enums.RequestStatus;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.RequestDto;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.entities.Request;
import com.azo.backend.msvc.binnacle.msvc_binnacle.services.RequestService;

import feign.FeignException;
import jakarta.validation.Valid;

//5. Se crea el ProcedureController

@RestController
@RequestMapping("/api/v1/requests")
public class RequestController {

  @Autowired
  private RequestService service;

  @GetMapping
  public ResponseEntity<List<RequestDto>> list () {
    return ResponseEntity.ok(service.findAll());
  }

  //listar todos los tramites con paginación
  @GetMapping("/page/{page}")
  public Page<RequestDto> list(@PathVariable Integer page){
    Pageable pageable = PageRequest.of(page, 5);
    return service.findAll(pageable);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> detail(@PathVariable Long id) {
    Optional<RequestDto> o = service.findById(id);
    if(o.isPresent()) {
      return ResponseEntity.ok(o.orElseThrow());
    }
    return ResponseEntity.notFound().build();
  }

  @PostMapping
  public ResponseEntity<?> create (@Valid @RequestBody Request request, BindingResult result) {
    if(result.hasErrors()){
      return validate(result);
    }

    try {
      RequestDto createdRequest = service.save(request);
      return ResponseEntity.status(HttpStatus.CREATED).body(createdRequest);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("Error al crear la solicitud: " + e.getMessage());
    }
    //return ResponseEntity.status(HttpStatus.CREATED).body(service.save(request));
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> update(@Valid @RequestBody Request request,
  BindingResult result,  
  @PathVariable Long id) {
    if(result.hasErrors()){
      return validate(result);
    }

    try {
      RequestDto updatedRequest = service.update(id, request);
      return ResponseEntity.ok(updatedRequest);
    } catch (Exception e) {
        return ResponseEntity.notFound().build();
    }
    //return ResponseEntity.status(HttpStatus.CREATED).body(service.update(id, request));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> remove (@PathVariable Long id){
    Optional<RequestDto> o = service.findById(id);
    if(o.isPresent()){
      service.remove(id);
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.notFound().build();
  }

  @GetMapping("/status/{status}")
  public ResponseEntity<?> getRequestsByStatus(@PathVariable RequestStatus status) {
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
  public ResponseEntity<?> getRequestsByUser(@PathVariable Long userId) {
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
