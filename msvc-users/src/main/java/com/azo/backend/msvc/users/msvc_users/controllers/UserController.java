package com.azo.backend.msvc.users.msvc_users.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.azo.backend.msvc.users.msvc_users.models.entities.User;
import com.azo.backend.msvc.users.msvc_users.services.UserService;

import jakarta.validation.Valid;


//5. Se crea el UserController

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

  @Autowired
  private UserService service;

  @GetMapping
  public List<User> list(){
    return service.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> show (@PathVariable Long id){
    Optional<User> userOptional = service.findById(id);
    if(userOptional.isPresent()){
      return ResponseEntity.ok(userOptional.orElseThrow());
    }
    return ResponseEntity.notFound().build();
  }

  @PostMapping
  public ResponseEntity<?> create (@Valid @RequestBody User user, BindingResult result){

    if(result.hasErrors()){
      return validate(result);
    }
    if(!user.getEmail().isEmpty() && service.findByEmail(user.getEmail()).isPresent()){
      return ResponseEntity.badRequest()
        .body(Collections
          .singletonMap("message", "Ya existe un usuario con este correo electronico"));
    }
    return ResponseEntity.status(HttpStatus.CREATED).body(service.save(user));
  }


  @PutMapping("/{id}")
  public ResponseEntity<?> update (@Valid @RequestBody User user, BindingResult result, @PathVariable Long id){
    if(result.hasErrors()){
      return validate(result);
    }
    Optional<User> o = service.findById(id);
    if(o.isPresent()){
      User userDb = o.orElseThrow();

      if(!user.getEmail().isEmpty() && !user.getEmail().equalsIgnoreCase(userDb.getEmail()) && service.findByEmail(user.getEmail()).isPresent()){
        return ResponseEntity.badRequest()
          .body(Collections
            .singletonMap("message", "Ya existe un usuario con este correo electronico"));
      }

      userDb.setUsername(user.getUsername());
      userDb.setPassword(user.getPassword());
      userDb.setEmail(user.getEmail());
      userDb.setCellphone(user.getCellphone());
      userDb.setPhone(user.getPhone());
      userDb.setAvatar(user.getAvatar());
      userDb.setcreatedAt(user.getcreatedAt());
      return ResponseEntity.status(HttpStatus.CREATED).body(service.save(userDb));
    }
    return ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> remove (@PathVariable Long id){
    Optional<User> o = service.findById(id);
    if(o.isPresent()){
      service.remove(id);
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.notFound().build();
  }


  //metodos utilitario para validar que un error si se repite el user o email
  private ResponseEntity<Map<String, String>> validate (BindingResult result){
    Map<String, String> errors = new HashMap<>();
    result.getFieldErrors().forEach( err -> {
      errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
    });
    return ResponseEntity.badRequest().body(errors);
  }


}
