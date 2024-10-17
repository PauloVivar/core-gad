package com.azo.backend.msvc.users_prod.msvc_users_prod.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class OAuthSetupController {

  @Value("${gmail.client.id}")
  private String clientId;

  @Value("${gmail.client.secret}")
  private String clientSecret;

  @Value("${gmail.redirect.uri}")
  private String redirectUri;

  private final RestTemplate restTemplate = new RestTemplate();
  private final ObjectMapper objectMapper = new ObjectMapper();

  @GetMapping("/setup/oauth")
  public String setupOAuth() {
      String authUrl = "https://accounts.google.com/o/oauth2/auth" +
              "?client_id=" + clientId +
              "&redirect_uri=" + redirectUri +
              "&scope=https://www.googleapis.com/auth/gmail.send" +
              "&response_type=code" +
              "&access_type=offline" +
              "&prompt=consent";
      return "Por favor, visita esta URL para autorizar la aplicación: " + authUrl;
  }

  @GetMapping("/oauth2/callback")
  public ResponseEntity<String> handleCallback(@RequestParam("code") String code) {
      try {
          String tokenResponse = exchangeCodeForTokens(code);
          JsonNode jsonNode = objectMapper.readTree(tokenResponse);
          String refreshToken = jsonNode.get("refresh_token").asText();

          // Guardar el refresh token en un archivo .env
          saveRefreshToken(refreshToken);

          return ResponseEntity.ok("Refresh Token obtenido y guardado en .env. Por favor, reinicia la aplicación.");
      } catch (Exception e) {
          return ResponseEntity.badRequest().body("Error: " + e.getMessage());
      }
  }

  private String exchangeCodeForTokens(String code) {
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

      MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
      map.add("client_id", clientId);
      map.add("client_secret", clientSecret);
      map.add("code", code);
      map.add("redirect_uri", redirectUri);
      map.add("grant_type", "authorization_code");

      HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

      return restTemplate.postForObject(
          "https://oauth2.googleapis.com/token",
          request,
          String.class
      );
  }

  private void saveRefreshToken(String refreshToken) throws IOException {
      String envContent = "GMAIL_REFRESH_TOKEN=" + refreshToken + "\n";
      Files.write(Paths.get(".env"), envContent.getBytes());
  }
  
}
