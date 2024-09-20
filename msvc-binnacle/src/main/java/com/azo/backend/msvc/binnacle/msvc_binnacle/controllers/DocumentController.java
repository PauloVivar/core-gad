package com.azo.backend.msvc.binnacle.msvc_binnacle.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

import com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.DocumentDto;
import com.azo.backend.msvc.binnacle.msvc_binnacle.services.DocumentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

//5. Se crea el ProcedureController

@RestController
@RequestMapping("/api/v1/requests/{requestId}/documents")
@Tag(name = "DocumentController", description = "Operaciones relacionadas con los documentos")
public class DocumentController {

  @Autowired
  private DocumentService service;

  @GetMapping
  @Operation(summary = "Obtener documentos", description = "Devuelve una lista de todas los documentos registradas")
  public ResponseEntity<List<DocumentDto>> list (
          @Parameter(description = "ID de la solicitud", required = true)   
          @PathVariable Long requestId) {
    return ResponseEntity.ok(service.findAllByRequestId(requestId));
  }

  @GetMapping("/page/{page}")
  @Operation(summary = "Obtener documentos paginados", description = "Devuelve una lista paginada de documentos")
  public ResponseEntity<Page<DocumentDto>> list(
          @Parameter(description = "ID de la solicitud", required = true)         
          @PathVariable Long requestId, 
          @Parameter(description = "ID de la pagina", required = true) 
          @PathVariable Integer page){
      Pageable pageable = PageRequest.of(page, 5);
      Page<DocumentDto> documents = service.findAllPageByRequestId(requestId, pageable);
      return ResponseEntity.ok(documents);
  }

  @PostMapping
  @Operation(summary = "Adjuntar nuevo documento", description = "Crea un nuevo documento a partir de una solicitud")
  public ResponseEntity<?> create ( 
          @Parameter(description = "ID de la solicitud", required = true) 
          @PathVariable Long requestId, 
          @Valid @RequestBody DocumentDto documentDto, 
            BindingResult result) {
    if(result.hasErrors()){
      return validate(result);
    }

    try {
      documentDto.setRequestId(requestId);
      DocumentDto savedDocument = service.save(documentDto);
      return ResponseEntity.status(HttpStatus.CREATED).body(savedDocument);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("Error al cargar el documento: " + e.getMessage());
    }
    //return ResponseEntity.status(HttpStatus.CREATED).body(service.save(request));
  }

  @PutMapping("/{id}")
  @Operation(summary = "Actualizar documento", description = "Actualiza un documento existente")
  public ResponseEntity<?> update(
          @Parameter(description = "ID de la solicitud", required = true) 
          @PathVariable Long requestId,
          @Parameter(description = "ID del documento", required = true) 
          @PathVariable Long id,
          @Valid @RequestBody DocumentDto documentDto,
            BindingResult result) {
    if(result.hasErrors()){
      return validate(result);
    }

    try {
      documentDto.setRequestId(requestId);
      return service.update(documentDto, id)
              .map(ResponseEntity::ok)
              .orElse(ResponseEntity.notFound().build());
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    //return ResponseEntity.status(HttpStatus.CREATED).body(service.update(id, request));
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Eliminar documento", description = "Elimina un documento existente")
  public ResponseEntity<?> remove (
          @Parameter(description = "ID de la solicitud", required = true) 
          @PathVariable Long requestId,
          @Parameter(description = "ID del documento", required = true)
          @PathVariable Long id){

    Optional<DocumentDto> o = service.findById(id);
    if(o.isPresent()){
      service.remove(id);
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.notFound().build();
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
