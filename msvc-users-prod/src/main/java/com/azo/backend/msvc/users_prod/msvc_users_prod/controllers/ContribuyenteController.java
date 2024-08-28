package com.azo.backend.msvc.users_prod.msvc_users_prod.controllers;

import java.util.HashMap;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.azo.backend.msvc.users_prod.msvc_users_prod.models.dto.ContribuyenteDto;
import com.azo.backend.msvc.users_prod.msvc_users_prod.models.dto.UserDetailDto;
import com.azo.backend.msvc.users_prod.msvc_users_prod.models.dto.UserDto;
import com.azo.backend.msvc.users_prod.msvc_users_prod.models.entities.Contribuyente;
import com.azo.backend.msvc.users_prod.msvc_users_prod.models.entities.User;
import com.azo.backend.msvc.users_prod.msvc_users_prod.services.ContribuyenteService;
import com.azo.backend.msvc.users_prod.msvc_users_prod.services.UserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PutMapping;


//5. Quinto Create Controller -> Mapeo de endpoints, finalización del CRUD

@RestController
@RequestMapping("/api/v1/taxpayers")
//@CrossOrigin(origins = "http://localhost:5173/")  //cors
@CrossOrigin(originPatterns = "*")                  //cors solo pruebas
public class ContribuyenteController {

  @Autowired
  private ContribuyenteService service;

  @Autowired
  private UserService userService;

  //listar todos los taxpayers con paginación
  @GetMapping("/page/{page}")
  public Page<ContribuyenteDto> list(@PathVariable Integer page){
    Pageable pageable = PageRequest.of(page, 5);
    return service.findAll(pageable);
  }

  //getById -> orElseThrow()
  @GetMapping("/{ci}")
  public ResponseEntity<?> detail (@PathVariable String ci){
    Optional<ContribuyenteDto> ContribuyenteOptional = service.findById(ci);
    if(ContribuyenteOptional.isPresent()){
      return ResponseEntity.ok(ContribuyenteOptional.orElseThrow());
    }
    return ResponseEntity.notFound().build();
  }

  // Nuevo endpoint para verificar si existe un contribuyente
  // @GetMapping("/check/{ci}")
  // public ResponseEntity<?> checkContribuyenteExists(@PathVariable String ci) {
  //     boolean exists = service.existsByCi(ci);
  //     return ResponseEntity.ok(Map.of("exists", exists));
  // }
  @GetMapping("/check/{ci}")
  public ResponseEntity<Boolean> checkContribuyenteExists(@PathVariable String ci) {
      boolean exists = service.existsByCi(ci);
      return ResponseEntity.ok(exists);
  }

  //post
  @PostMapping
  public ResponseEntity<?> create (@Valid @RequestBody ContribuyenteDto contribuyenteDto, 
                                BindingResult result){
    if(result.hasErrors()){
      return validation(result);
    }
    // Verificar si el contribuyente ya existe
    if (service.existsByCi(contribuyenteDto.getCi())) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El contribuyente ya existe");
    }

    Contribuyente contribuyente = new Contribuyente();
    contribuyente.setCi(contribuyenteDto.getCi());                                      //idContribuyente
    contribuyente.setFullName(contribuyenteDto.getFullName());
    contribuyente.setAddress(contribuyenteDto.getAddress());
    contribuyente.setPhone(contribuyenteDto.getPhone());
    //contribuyente.setContributionDate(contribuyenteDto.getContributionDate());
    //contribuyente.setResponsibleUser(contribuyenteDto.getResponsibleUser());
    contribuyente.setIndicatorExoneration(contribuyenteDto.getIndicatorExoneration());
    contribuyente.setReasonExoneration(contribuyenteDto.getReasonExoneration());
    contribuyente.setTaxpayerStatus(contribuyenteDto.getTaxpayerStatus());
    contribuyente.setTaxpayerCity(contribuyenteDto.getTaxpayerCity());
    contribuyente.setHouseNumber(contribuyenteDto.getHouseNumber());
    contribuyente.setTaxpayerType(contribuyenteDto.getTaxpayerType());
    //contribuyente.setContributionTime(contribuyenteDto.getContributionTime());
    //contribuyente.setWorkstation(contribuyenteDto.getWorkstation());
    //contribuyente.setEmail(contribuyenteDto.getEmail());
    contribuyente.setLegalPerson(contribuyenteDto.getLegalPerson());
    contribuyente.setIdentificationType(contribuyenteDto.getIdentificationType());
    contribuyente.setBirthdate(contribuyenteDto.getBirthdate());
    contribuyente.setDisabilityPercentage(contribuyenteDto.getDisabilityPercentage());
    contribuyente.setMaritalStatus(contribuyenteDto.getMaritalStatus());
    //contribuyente.setSpouse(contribuyenteDto.getSpouse());
    //contribuyente.setPassword(contribuyenteDto.getPassword());

    // Si se proporciona un userId, asociar el User existente
    if (contribuyenteDto.getUserId() != null) {
      Optional<UserDetailDto> userOptional = userService.findById(contribuyenteDto.getUserId());
      if (userOptional.isPresent()) {
          // Se convierte UserDto a User
          User user = convertToUser(userOptional.get());
          contribuyente.setUser(user);
      } else {
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El usuario especificado no existe");
      }
    }

    // Guardar el contribuyente
    ContribuyenteDto savedContribuyente = service.save(contribuyente);
    return ResponseEntity.status(HttpStatus.CREATED).body(savedContribuyente);
  }

  //update
  @PutMapping("/{ci}")
  public ResponseEntity<?> update (@Valid @RequestBody ContribuyenteDto contribuyenteDto, 
                                BindingResult result, 
                                @PathVariable String ci){
    if(result.hasErrors()){
      return validation(result);
    }

    Optional<ContribuyenteDto> contribuyenteOptional = service.findById(ci);
      if (contribuyenteOptional.isPresent()) {
        Contribuyente contribuyente = convertToEntity(contribuyenteDto);
        contribuyente.setCi(ci); // Asegurarse de que el CI no cambie

        // Manejar la asociación con User
        if (contribuyenteDto.getUserId() != null) {
            Optional<UserDetailDto> userOptional = userService.findById(contribuyenteDto.getUserId());
            if (userOptional.isPresent()) {
                User user = convertToUser(userOptional.get());
                contribuyente.setUser(user);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El usuario especificado no existe");
            }
        } else {
            contribuyente.setUser(null); // Desasociar el usuario si no se proporciona userId
        }

        Optional<ContribuyenteDto> updatedContribuyente = service.update(contribuyente, ci);
        return ResponseEntity.ok(updatedContribuyente.orElseThrow());
      }
      return ResponseEntity.notFound().build();
  }

  // ***** metodos utils
  // validar entrada de data
  private ResponseEntity<?> validation(BindingResult result) {
    Map<String, String> errors = new HashMap<>();
    result.getFieldErrors().forEach(err -> {
      //errors.put(err.getField(), "El campo" + err.getField() + " " + err.getDefaultMessage());
      errors.put(err.getField(), err.getDefaultMessage());
    });
    return ResponseEntity.badRequest().body(errors);
  }

  private Contribuyente convertToEntity(ContribuyenteDto dto) {
    Contribuyente contribuyente = new Contribuyente();
    contribuyente.setCi(dto.getCi());
    contribuyente.setFullName(dto.getFullName());
    contribuyente.setAddress(dto.getAddress());
    contribuyente.setPhone(dto.getPhone());
    
    contribuyente.setIndicatorExoneration(dto.getIndicatorExoneration());
    contribuyente.setReasonExoneration(dto.getReasonExoneration());
    contribuyente.setTaxpayerStatus(dto.getTaxpayerStatus());
    contribuyente.setTaxpayerCity(dto.getTaxpayerCity());
    contribuyente.setHouseNumber(dto.getHouseNumber());
    contribuyente.setTaxpayerType(dto.getTaxpayerType());
    
    contribuyente.setLegalPerson(dto.getLegalPerson());
    contribuyente.setIdentificationType(dto.getIdentificationType());
    contribuyente.setBirthdate(dto.getBirthdate());
    contribuyente.setDisabilityPercentage(dto.getDisabilityPercentage());
    contribuyente.setMaritalStatus(dto.getMaritalStatus());

    return contribuyente;
  }

  // Se convierte UserDto a User
  private User convertToUser(UserDto userDto) {
    User user = new User();
    user.setId(userDto.getId());
    user.setUsername(userDto.getUsername());
    user.setEmail(userDto.getEmail());
    user.setAdmin(false);
    user.setAvatar(userDto.getAvatar());
    user.setStatus(userDto.getStatus());
    //user.setCustomer(userDto.getCustomer());

    return user;
  }

}