package com.azo.backend.msvc.avaluos_catastros.msvc_avaluos_catastros.models;

import java.time.LocalDate;

//import java.time.LocalDateTime;

public class Customer {

  private Long id;
  private String firstname;
  private String lastname;
  private String documentId;
  private String typeDocumentId;
  
  private LocalDate birthdate;
  private String gender;
  private String civilStatus;
  private String disability;
  private String ethnicity;


  //getters and setters
  
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
  public LocalDate getBirthdate() {
    return birthdate;
  }
  public void setBirthdate(LocalDate birthdate) {
    this.birthdate = birthdate;
  }
  public String getGender() {
    return gender;
  }
  public void setGender(String gender) {
    this.gender = gender;
  }
  public String getCivilStatus() {
    return civilStatus;
  }
  public void setCivilStatus(String civilStatus) {
    this.civilStatus = civilStatus;
  }
  public String getDisability() {
    return disability;
  }
  public void setDisability(String disability) {
    this.disability = disability;
  }
  public String getEthnicity() {
    return ethnicity;
  }
  public void setEthnicity(String ethnicity) {
    this.ethnicity = ethnicity;
  }

}
