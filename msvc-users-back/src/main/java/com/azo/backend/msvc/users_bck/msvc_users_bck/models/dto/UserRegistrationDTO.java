package com.azo.backend.msvc.users_bck.msvc_users_bck.models.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class UserRegistrationDTO {

  @NotBlank(message = "El username es requerido.")
  @Column(nullable = false, unique = true)
  private String username;

  @NotBlank(message = "El password es requerido.")
  @Column(nullable = false)
  private String password;

  @NotEmpty(message = "El email es requerido.")
  @Email(message = "Ingrese un email válido.")
  @Column(nullable = false, unique = true)
  private String email;

  @NotNull(message = "La aceptación de los términos de servicio es requerida.")
  private Boolean acceptedTerms;

  // ***test Nuevos campos para Customer
  @NotBlank(message = "El nombre es requerido.")
  private String firstname;

  @NotBlank(message = "El apellido es requerido.")
  private String lastname;

  @NotBlank(message = "El documento de identificación es requerido.")
  private String documentId;

  @NotBlank(message = "Elija el tipo de documento de identificación.")
  private String typeDocumentId;

  // Constructor
  public UserRegistrationDTO() {
  }

  public UserRegistrationDTO(String username, String password, String email,  boolean acceptedTerms, String firstname, String lastname, String documentId, String typeDocumentId ) {
    this.username = username;
    this.password = password;
    this.email = email;
    this.acceptedTerms = acceptedTerms;
    this.firstname = firstname;
    this.lastname = lastname;
    this.documentId = documentId;
    this.typeDocumentId = typeDocumentId;
  }

  // Getters y Setters
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

  public Boolean getAcceptedTerms() {
    return acceptedTerms;
  }

  public void setAcceptedTerms(Boolean acceptedTerms) {
    this.acceptedTerms = acceptedTerms;
  }

  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public String getDocumentId() {
    return documentId;
  }

  public void setDocumentId(String documentId) {
    this.documentId = documentId;
  }

  public String getTypeDocumentId() {
    return typeDocumentId;
  }

  public void setTypeDocumentId(String typeDocumentId) {
    this.typeDocumentId = typeDocumentId;
  }

}
