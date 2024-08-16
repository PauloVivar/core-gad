package com.azo.backend.msvc.users_prod.msvc_users_prod.models.dto;

import java.util.List;

public class CustomerDto {
  private Long id;
  private String firstname;
  private String lastname;
  private String documentId;
  private String typeDocumentId;
  private Long userId;
  private List<AddressDto> addresses;

  public CustomerDto() {
  }

  // Constructor sin id para creación
  public CustomerDto(String firstname, String lastname, String documentId, String typeDocumentId, Long userId) {
    this.firstname = firstname;
    this.lastname = lastname;
    this.documentId = documentId;
    this.typeDocumentId = typeDocumentId;
    this.userId = userId;
  }

  // Constructor completo para respuestas
  public CustomerDto(Long id, String firstname, String lastname, String documentId, String typeDocumentId, Long userId) {
    this.id = id;
    this.firstname = firstname;
    this.lastname = lastname;
    this.documentId = documentId;
    this.typeDocumentId = typeDocumentId;
    this.userId = userId;
  }

  // Constructor que incluye addresses
  public CustomerDto(Long id, String firstname, String lastname, String documentId, String typeDocumentId, Long userId, List<AddressDto> addresses) {
    this(id, firstname, lastname, documentId, typeDocumentId, userId);
    this.addresses = addresses;
  }


  // Getters and setters
  
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public List<AddressDto> getAddresses() {
    return addresses;
  }

  public void setAddresses(List<AddressDto> addresses) {
    this.addresses = addresses;
  }

}
