package com.azo.backend.msvc.users_prod.msvc_users_prod.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.azo.backend.msvc.users_prod.msvc_users_prod.models.dto.AddressDto;
import com.azo.backend.msvc.users_prod.msvc_users_prod.services.AddressService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PutMapping;


//5. Quinto Create Controller -> Mapeo de endpoints, finalización del CRUD

@RestController
@RequestMapping("/api/v1/addresses")
//@CrossOrigin(origins = "http://localhost:5173/")  //cors
@CrossOrigin(originPatterns = "*")                  //cors solo pruebas
public class AddressController {

  @Autowired
  private AddressService service;

  @GetMapping("/{id}")
  public ResponseEntity<AddressDto> getAddress(@PathVariable Long id) {
    AddressDto address = service.findById(id);
    return ResponseEntity.ok(address);
  }

  @GetMapping
  public ResponseEntity<List<AddressDto>> getAllAddresses() {
    List<AddressDto> addresses = service.findAll();
    return ResponseEntity.ok(addresses);
  }

  @PutMapping("/{id}")
  public ResponseEntity<AddressDto> updateAddress(@PathVariable Long id, @Valid @RequestBody AddressDto addressDto) {
    AddressDto updatedAddress = service.update(id, addressDto);
    return ResponseEntity.ok(updatedAddress);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
    service.deleteById(id);
    return ResponseEntity.noContent().build();
  }

}