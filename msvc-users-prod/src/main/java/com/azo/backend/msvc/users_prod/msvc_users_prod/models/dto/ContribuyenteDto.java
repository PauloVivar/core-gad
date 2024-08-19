package com.azo.backend.msvc.users_prod.msvc_users_prod.models.dto;

import java.time.LocalDate;
//import java.time.LocalTime;

public class ContribuyenteDto {
  private String ci;
  private String fullName;
  private String address;
  private String phone;
  //private LocalDate contributionDate;
  //private String responsibleUser;
  private Boolean indicatorExoneration;    // 0
  private String reasonExoneration;
  private String taxpayerStatus;           // ACTIVO
  private String taxpayerCity;
  private String houseNumber;
  private Integer taxpayerType;            // Normal = 0, Proveedor, Empleado, Trabajador
  //private LocalTime contributionTime;
  //private String workstation;
  //private String email;
  private Integer legalPerson;             // Persona Natural = 44, Juridica derecho privado, Juridica derecho publico (lut_listasitems)
  private Integer identificationType;      // Cedula = 33, Pasaporte (lut_listasitems)
  private LocalDate birthdate;
  private Integer disabilityPercentage;    // 0
  private Integer maritalStatus;           // Soltero = 37, Casado, Viudo, Unión libre (lut_listasitems)
  //private String spouse;
  //private String password;
  private Long userId;

  public ContribuyenteDto() {
  }

  public ContribuyenteDto(
    String ci, 
    String fullName, 
    String address, 
    String phone, 
    //LocalDate contributionDate, 
    //String responsibleUser, 
    Boolean indicatorExoneration,
    String reasonExoneration,
    String taxpayerStatus,
    String taxpayerCity,
    String houseNumber,
    Integer taxpayerType,
    //LocalTime contributionTime,
    //String workstation,
    //String email,
    Integer legalPerson,
    Integer identificationType,
    LocalDate birthdate,
    Integer disabilityPercentage,
    Integer maritalStatus,
    //String spouse,
    //String password,
    Long userId
    ) {
    this.ci = ci;
    this.fullName = fullName;
    this.address = address;
    this.phone = phone;
    //this.contributionDate = contributionDate;
    //this.responsibleUser = responsibleUser;
    this.indicatorExoneration = indicatorExoneration;
    this.reasonExoneration = reasonExoneration;
    this.taxpayerStatus = taxpayerStatus;
    this.taxpayerCity = taxpayerCity;
    this.houseNumber = houseNumber;
    this.taxpayerType = taxpayerType;
    //this.contributionTime = contributionTime;
    //this.workstation = workstation;
    //this.email = email;
    this.legalPerson = legalPerson;
    this.identificationType = identificationType;
    this.birthdate = birthdate;
    this.disabilityPercentage = disabilityPercentage;
    this.maritalStatus = maritalStatus;
    //this.spouse = spouse;
    //this.password = password;
    this.userId = userId;
  }

  //Getters and Setters
  
  public String getCi() {
    return ci;
  }

  public void setCi(String ci) {
    this.ci = ci;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  // public LocalDate getContributionDate() {
  //   return contributionDate;
  // }

  // public void setContributionDate(LocalDate contributionDate) {
  //   this.contributionDate = contributionDate;
  // }

  // public String getResponsibleUser() {
  //   return responsibleUser;
  // }

  // public void setResponsibleUser(String responsibleUser) {
  //   this.responsibleUser = responsibleUser;
  // }

  public Boolean getIndicatorExoneration() {
    return indicatorExoneration;
  }

  public void setIndicatorExoneration(Boolean indicatorExoneration) {
    this.indicatorExoneration = indicatorExoneration;
  }

  public String getReasonExoneration() {
    return reasonExoneration;
  }

  public void setReasonExoneration(String reasonExoneration) {
    this.reasonExoneration = reasonExoneration;
  }

  public String getTaxpayerStatus() {
    return taxpayerStatus;
  }

  public void setTaxpayerStatus(String taxpayerStatus) {
    this.taxpayerStatus = taxpayerStatus;
  }

  public String getTaxpayerCity() {
    return taxpayerCity;
  }

  public void setTaxpayerCity(String taxpayerCity) {
    this.taxpayerCity = taxpayerCity;
  }

  public String getHouseNumber() {
    return houseNumber;
  }

  public void setHouseNumber(String houseNumber) {
    this.houseNumber = houseNumber;
  }

  public Integer getTaxpayerType() {
    return taxpayerType;
  }

  public void setTaxpayerType(Integer taxpayerType) {
    this.taxpayerType = taxpayerType;
  }

  // public LocalTime getContributionTime() {
  //   return contributionTime;
  // }

  // public void setContributionTime(LocalTime contributionTime) {
  //   this.contributionTime = contributionTime;
  // }

  // public String getWorkstation() {
  //   return workstation;
  // }

  // public void setWorkstation(String workstation) {
  //   this.workstation = workstation;
  // }

  public Integer getLegalPerson() {
    return legalPerson;
  }

  public void setLegalPerson(Integer legalPerson) {
    this.legalPerson = legalPerson;
  }

  public Integer getIdentificationType() {
    return identificationType;
  }

  public void setIdentificationType(Integer identificationType) {
    this.identificationType = identificationType;
  }

  public LocalDate getBirthdate() {
    return birthdate;
  }

  public void setBirthdate(LocalDate birthdate) {
    this.birthdate = birthdate;
  }

  public Integer getDisabilityPercentage() {
    return disabilityPercentage;
  }

  public void setDisabilityPercentage(Integer disabilityPercentage) {
    this.disabilityPercentage = disabilityPercentage;
  }

  public Integer getMaritalStatus() {
    return maritalStatus;
  }

  public void setMaritalStatus(Integer maritalStatus) {
    this.maritalStatus = maritalStatus;
  }

  // public String getSpouse() {
  //   return spouse;
  // }

  // public void setSpouse(String spouse) {
  //   this.spouse = spouse;
  // }

  // public String getPassword() {
  //   return password;
  // }

  // public void setPassword(String password) {
  //   this.password = password;
  // }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  
}
