package com.azo.backend.msvc.users_prod.msvc_users_prod.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "gmail")
public class GmailProperties {

  private String clientId;
  private String clientSecret;
  private String refreshToken;
  private String email;

  // Getters and setters
  public String getClientId() { return clientId; }
  public void setClientId(String clientId) { this.clientId = clientId; }
  public String getClientSecret() { return clientSecret; }
  public void setClientSecret(String clientSecret) { this.clientSecret = clientSecret; }
  public String getRefreshToken() { return refreshToken; }
  public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }
  public String getEmail() { return email; }
  public void setEmail(String email) { this.email = email; }
  
}
