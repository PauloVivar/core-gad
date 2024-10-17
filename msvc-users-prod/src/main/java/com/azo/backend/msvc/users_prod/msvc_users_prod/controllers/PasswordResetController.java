package com.azo.backend.msvc.users_prod.msvc_users_prod.controllers;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.azo.backend.msvc.users_prod.msvc_users_prod.models.dto.PasswordResetDto;
import com.azo.backend.msvc.users_prod.msvc_users_prod.models.dto.PasswordResetRequestDto;
import com.azo.backend.msvc.users_prod.msvc_users_prod.models.entities.User;
import com.azo.backend.msvc.users_prod.msvc_users_prod.services.UserService;

import jakarta.validation.Valid;

//5. Quinto Create Controller -> Mapeo de endpoints, finalización del CRUD

@RestController
@RequestMapping("/api/v1/password")
//@CrossOrigin(origins = "http://localhost:5173/")  //cors
@CrossOrigin(originPatterns = "*")                  //cors solo pruebas
public class PasswordResetController {
  
  @Autowired
  private UserService userService;

  @Autowired
  private JwtDecoder jwtDecoder;

  @PostMapping("/reset-request")
  public ResponseEntity<?> resetPasswordRequest(
      @Valid 
      @RequestBody 
      PasswordResetRequestDto requestDto) {
    return userService.findByEmail(requestDto.getEmail())
        .map(user -> {
            @SuppressWarnings("unused")
            String code = userService.createPasswordResetCodeForUser(user);
            // Aquí no devolvemos el código por razones de seguridad
            return ResponseEntity.ok("Se ha enviado un código de verificación a su correo electrónico.");
        })
        .orElse(ResponseEntity.badRequest().body("Usuario no encontrado"));
  }

  @PostMapping("/reset")
  public ResponseEntity<?> resetPassword(
      @Valid 
      @RequestBody 
      PasswordResetDto resetDto,
      @RequestHeader("Authorization") String authHeader) {

    String token = authHeader.replace("Bearer ", "");
    if (!validateToken(token)) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido o expirado");
    }

    String email = getEmailFromToken(token);
    User user = userService.findByEmail(email).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

    String result = userService.validatePasswordResetCode(resetDto.getCode());
    if (result != null) {
        return ResponseEntity.badRequest().body("Código inválido o expirado");
    }

    userService.changeUserPassword(user, resetDto.getNewPassword());
    return ResponseEntity.ok("Contraseña cambiada exitosamente");
  }

  private boolean validateToken(String token) {
    try {
      Jwt jwt = jwtDecoder.decode(token);
      Instant now = Instant.now();
      Instant expiresAt = jwt.getExpiresAt();
      return expiresAt != null && !expiresAt.isBefore(now) && 
             "password_reset".equals(jwt.getClaim("purpose"));
    } catch (JwtException e) {
        return false;
    }
  }

  private String getEmailFromToken(String token) {
    Jwt jwt = jwtDecoder.decode(token);
    return jwt.getSubject();
  }

}