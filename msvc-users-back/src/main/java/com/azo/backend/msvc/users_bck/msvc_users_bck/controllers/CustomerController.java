package com.azo.backend.msvc.users_bck.msvc_users_bck.controllers;

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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.azo.backend.msvc.users_bck.msvc_users_bck.models.dto.CustomerDto;
import com.azo.backend.msvc.users_bck.msvc_users_bck.models.entities.Customer;
import com.azo.backend.msvc.users_bck.msvc_users_bck.services.CustomerService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PutMapping;


//5. Quinto Create Controller -> Mapeo de endpoints, finalización del CRUD

@RestController
@RequestMapping("/api/v1/customers")
//@CrossOrigin(origins = "http://localhost:5173/")  //cors
@CrossOrigin(originPatterns = "*")                  //cors solo pruebas
public class CustomerController {

  @Autowired
  private CustomerService service;

  //listar todos los users
  @GetMapping
  public List<CustomerDto> list(){
    return service.findAll();
  }

  //método custom para paginación
  @GetMapping("/page/{page}")
  public Page<CustomerDto> list(@PathVariable Integer page){
    Pageable pageable = PageRequest.of(page, 5);
    return service.findAll(pageable);
  }

  //getById -> orElseThrow()
  @GetMapping("/{id}")
  public ResponseEntity<?> show (@PathVariable Long id){
    Optional<CustomerDto> customerOptional = service.findById(id);
    if(customerOptional.isPresent()){
      return ResponseEntity.ok(customerOptional.orElseThrow());
    }
    return ResponseEntity.notFound().build();
  }

  //post
  @PostMapping
  public ResponseEntity<?> create (@Valid @RequestBody Customer customer, BindingResult result){
    if(result.hasErrors()){
      return validation(result);
    }
    // Verificar si el IdDocument ya existe en la base de datos
    if (service.existsByIdDocument(customer.getDocumentId())) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Campo ya existe");
    }
    // Guardar el cliente si no existe
    CustomerDto customerDb = service.save(customer);
    return ResponseEntity.status(HttpStatus.CREATED).body(customerDb);
  }

  //update
  @PutMapping("/{id}")
  public ResponseEntity<?> update (@Valid @RequestBody Customer customer, BindingResult result, @PathVariable Long id){
    if(result.hasErrors()){
      return validation(result);
    }
    Optional<CustomerDto> customerOptional = service.update(customer, id);
    if(customerOptional.isPresent()){
      return ResponseEntity.status(HttpStatus.CREATED).body(customerOptional.orElseThrow());
    }
    return ResponseEntity.notFound().build();
  }

  //delete
  @DeleteMapping("/{id}")
  public ResponseEntity<?> remove (@PathVariable Long id){
    Optional<CustomerDto> customerOptional = service.findById(id);
    if(customerOptional.isPresent()){
      service.remove(id);
      return ResponseEntity.noContent().build(); //204
    }
    return ResponseEntity.notFound().build();    //404
  }

  //metodo para validar entrada de data
  private ResponseEntity<?> validation(BindingResult result) {
    Map<String, String> errors = new HashMap<>();
    result.getFieldErrors().forEach(err -> {
      //errors.put(err.getField(), "El campo" + err.getField() + " " + err.getDefaultMessage());
      errors.put(err.getField(), err.getDefaultMessage());
    });
    return ResponseEntity.badRequest().body(errors);
  }

}