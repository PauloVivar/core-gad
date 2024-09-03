package com.azo.backend.msvc.avaluos_catastros.msvc_avaluos_catastros.controllers;

import java.util.Collections;
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

import com.azo.backend.msvc.avaluos_catastros.msvc_avaluos_catastros.models.User;
import com.azo.backend.msvc.avaluos_catastros.msvc_avaluos_catastros.models.entities.Procedure;
import com.azo.backend.msvc.avaluos_catastros.msvc_avaluos_catastros.services.ProcedureService;

import feign.FeignException;
import jakarta.validation.Valid;

//5. Se crea el ProcedureController

@RestController
@RequestMapping("/api/v1/procedures")
public class ProcedureController {

  @Autowired
  private ProcedureService service;

  @GetMapping
  public ResponseEntity<List<Procedure>> list () {
    return ResponseEntity.ok(service.findAll());
  }

  //listar todos los tramites con paginación
  @GetMapping("/page/{page}")
  public Page<Procedure> list(@PathVariable Integer page){
    Pageable pageable = PageRequest.of(page, 5);
    return service.findAll(pageable);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> detail(@PathVariable Long id) {
    Optional<Procedure> o = service.findByIdWithUsers(id); //service.findById(id);
    if(o.isPresent()) {
      return ResponseEntity.ok(o.orElseThrow());
    }
    return ResponseEntity.notFound().build();
  }

  @PostMapping
  public ResponseEntity<?> create (@Valid @RequestBody Procedure procedure, BindingResult result){
    if(result.hasErrors()){
      return validate(result);
    }
    return ResponseEntity.status(HttpStatus.CREATED).body(service.save(procedure));
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> update (@Valid @RequestBody Procedure procedure, 
      BindingResult result,
      @PathVariable Long id){
        
    if(result.hasErrors()){
      return validate(result);
    }
    Optional<Procedure> o = service.findById(id);

    if(o.isPresent()){
      Procedure procedureDb = o.orElseThrow();

      procedureDb.setStatus(procedure.getStatus());
      procedureDb.setTypeProcedure(procedure.getTypeProcedure());
      procedureDb.setStartDate(procedure.getStartDate());
      procedureDb.setEndDate(procedure.getEndDate());

      return ResponseEntity.status(HttpStatus.CREATED).body(service.save(procedureDb));
    }
    return ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> remove (@PathVariable Long id){
    Optional<Procedure> o = service.findById(id);
    if(o.isPresent()){
      service.remove(id);
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.notFound().build();
  }

  //microservicios
  @PutMapping("/assign-user/{procedureId}")
  public ResponseEntity<?> assignUser (@RequestBody User user, @PathVariable Long procedureId){
    Optional<User> o;
    try {
      o = service.assignUser(user, procedureId);
    } catch (FeignException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(Collections.singletonMap("message", "No existe el usuario por " + 
          "el id o error en la comunicación" + e.getMessage()));
    }
    if(o.isPresent()){
      return ResponseEntity.status(HttpStatus.CREATED).body(o.get());

    }
    return ResponseEntity.notFound().build();
  }

  @PostMapping("/create-user/{procedureId}")
  public ResponseEntity<?> createUser (@RequestBody User user, @PathVariable Long procedureId){
    Optional<User> o;
    try {
      o = service.createUser(user, procedureId);
    } catch (FeignException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(Collections.singletonMap("message", "No se pudo crear el usuario " + 
          "o error en la comunicación" + e.getMessage()));
    }
    if(o.isPresent()){
      return ResponseEntity.status(HttpStatus.CREATED).body(o.get());

    }
    return ResponseEntity.notFound().build();
  }

  @DeleteMapping("/remove-user/{procedureId}")
  public ResponseEntity<?> removeUser (@RequestBody User user, @PathVariable Long procedureId){
    Optional<User> o;
    try {
      o = service.removeUser(user, procedureId);
    } catch (FeignException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(Collections.singletonMap("message", "No se pudo remover el usuario " + 
          "o error en la comunicación" + e.getMessage()));
    }
    if(o.isPresent()){
      return ResponseEntity.status(HttpStatus.OK).body(o.get());

    }
    return ResponseEntity.notFound().build();
  }

  @DeleteMapping("/remove-procedure-user/{id}")

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
