package com.azo.backend.msvc.binnacle.msvc_binnacle.models;

//import java.time.LocalDateTime;
//paso 2: Usuarios para presentar el json de la comunicación con msvc_users_prod, no es una entidad

public class User {

  private Long id;
  private String username;
  private String password;
  private String email;
  private String contribuyenteCi;

  //getters and setters
  
  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  public String getUsername() {
    return username;
  }
  public void setUsername(String username) {
    this.username = username;
  }
  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
  }
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  public String getContribuyenteCi() {
    return contribuyenteCi;
  }
  public void setContribuyenteCi(String contribuyenteCi) {
    this.contribuyenteCi = contribuyenteCi;
  } 

}
