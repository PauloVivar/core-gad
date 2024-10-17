package com.azo.backend.msvc.users_prod.msvc_users_prod.controllers;

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

import com.azo.backend.msvc.users_prod.msvc_users_prod.models.dto.UserDetailDto;
import com.azo.backend.msvc.users_prod.msvc_users_prod.models.dto.UserDto;
import com.azo.backend.msvc.users_prod.msvc_users_prod.models.dto.UserRegistrationDTO;
import com.azo.backend.msvc.users_prod.msvc_users_prod.models.entities.User;
import com.azo.backend.msvc.users_prod.msvc_users_prod.models.request.UserRequest;
import com.azo.backend.msvc.users_prod.msvc_users_prod.services.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;



//5. Quinto Create Controller -> Mapeo de endpoints, finalización del CRUD

@RestController
@RequestMapping("/api/v1/users")
//@CrossOrigin(origins = "http://localhost:5173/")  //cors
@CrossOrigin(originPatterns = "*")                  //cors solo pruebas
public class UserController {

  @Autowired
  private UserService service;

  //listar todos los users
  @GetMapping
  public List<UserDto> list(){
    //solo pruebas
    // try {
    //   Thread.sleep(2000l);
    // } catch (InterruptedException e) {
    //   e.printStackTrace();
    // }
    return service.findAll();
  }

  //listar todos los users con paginación
  @GetMapping("/page/{page}")
  public Page<UserDto> list(@PathVariable Integer page){
    Pageable pageable = PageRequest.of(page, 5);
    return service.findAll(pageable);
  }

  //***Forma alternativa para el list users
  // @GetMapping("/{id}")
  // public User detail (@PathVariable Long id){
  //   return service.findById(id).orElseThrow();
  // }

  //buscar users por id
  //getById -> orElseThrow()
  @GetMapping("/{id}")
  public ResponseEntity<?> detail (@PathVariable Long id){
    Optional<UserDetailDto> userOptional = service.findById(id);
    if(userOptional.isPresent()){
      return ResponseEntity.ok(userOptional.orElseThrow());
    }
    return ResponseEntity.notFound().build();
  }

  @GetMapping("/search")
    public ResponseEntity<?> findByUsername(@RequestParam String username) {
        return service.findByUsername(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

  //***Forma alternativa para el post
  // @PostMapping
  // @ResponseStatus(HttpStatus.CREATED)
  // public User create (@RequestBody User user){
  //   return service.save(user);
  // }

  //guardar user
  @PostMapping
  public ResponseEntity<?> create (@Valid @RequestBody User user, BindingResult result){
    if(result.hasErrors()){
      return validation(result);
    }
    // Verificar si el username ya existe en la base de datos
    if (service.existsByUsername(user.getUsername())) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Campo ya existe");
    }
    // Guardar el usuario si no existe
    UserDto userDb = service.save(user);
    return ResponseEntity.status(HttpStatus.CREATED).body(userDb);
  }

  //guardar user registration
  @PostMapping("/registration")
  public ResponseEntity<?> createRegistration (@Valid @RequestBody UserRegistrationDTO userRegistration, 
                                            BindingResult result,
                                            HttpServletRequest request) {
    if (result.hasErrors()) {
      return validation(result);
    }

    if (userRegistration.getAcceptedTerms() == null || !userRegistration.getAcceptedTerms()) {
      return ResponseEntity.badRequest().body("Debe aceptar los términos de servicio para registrarse.");
    }

    // Verificar si el username ya existe en la base de datos
    if (service.existsByUsername(userRegistration.getUsername())) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El nombre de usuario ya existe.");
    }

    // Verificar si el email ya existe en la base de datos
    if (service.existsByEmail(userRegistration.getEmail())) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El email ya está registrado.");
    }

    if (service.isContribuyenteAssociated(userRegistration.getCi())) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El contribuyente ya está asociado a un usuario existente");
    }

    try {
      String ipAddress = getIpAddress(request);
      UserDto user = service.saveRegistration(userRegistration, ipAddress);
      return ResponseEntity.status(HttpStatus.CREATED).body(user);
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body("Error en el registro: " + e.getMessage());
    }
  }

  //obtener ip desde backend
  private String getIpAddress(HttpServletRequest request) {
    String ipAddress = request.getHeader("X-Forwarded-For");
    if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
        ipAddress = request.getHeader("Proxy-Client-IP");
    }
    if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
        ipAddress = request.getHeader("WL-Proxy-Client-IP");
    }
    if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
        ipAddress = request.getRemoteAddr();
    }
    return ipAddress;
  }

  //actualizar user
  @PutMapping("/{id}")
  public ResponseEntity<?> update (@Valid @RequestBody UserRequest user, 
                                BindingResult result, 
                                @PathVariable Long id){
    if(result.hasErrors()){
      return validation(result);
    }
    Optional<UserDto> userOptional = service.update(user, id);
    if(userOptional.isPresent()){
      return ResponseEntity.status(HttpStatus.CREATED).body(userOptional.orElseThrow());
    }
    return ResponseEntity.notFound().build();
  }

  //eliminar user
  @DeleteMapping("/{id}")
  public ResponseEntity<?> remove (@PathVariable Long id){
    Optional<UserDetailDto> useOptional = service.findById(id);
    if(useOptional.isPresent()){
      service.remove(id);
      return ResponseEntity.noContent().build(); //204
    }
    return ResponseEntity.notFound().build();    //404
  }

  //obtener usuarios por tramite
  @GetMapping("/users-by-procedure")
  public ResponseEntity<?> getUsersByProcedure(@RequestParam List<Long> ids) {
      return ResponseEntity.ok(service.listByIds(ids));
  }
  
  
  //METODOS AUXILIARES
  //metodo utils para validar entrada de data
  private ResponseEntity<?> validation(BindingResult result) {
    Map<String, String> errors = new HashMap<>();
    result.getFieldErrors().forEach(err -> {
      //errors.put(err.getField(), "El campo" + err.getField() + " " + err.getDefaultMessage());
      errors.put(err.getField(), err.getDefaultMessage());
    });
    return ResponseEntity.badRequest().body(errors);
  }

}