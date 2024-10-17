package com.azo.backend.msvc.users_prod.msvc_users_prod.controllers;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.azo.backend.msvc.users_prod.msvc_users_prod.models.dto.UserDto;
import com.azo.backend.msvc.users_prod.msvc_users_prod.services.UserService;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(originPatterns = "*")                  //cors solo pruebas
public class OAuthLoginController {

  @Autowired
  private UserService service;

  @GetMapping("/authorized")
  public Map<String, Object> authorized(@RequestParam String code) {
      return Collections.singletonMap("code", code);
  }

  @GetMapping("/login")
  public ResponseEntity<?> loginByUser(@RequestParam String username) {
    Optional<UserDto> o = service.findByUsername(username);
    
    if(o.isPresent()){
      return ResponseEntity.ok(o.orElseThrow());
    }
    return ResponseEntity.notFound().build();
  }
  
}
