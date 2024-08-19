package com.azo.backend.msvc.users_prod.msvc_users_prod.models.dto;

import java.time.LocalDate;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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

  // ***test Nuevos campos para Contribuyente
  @Id
  @NotBlank(message = "El documento de identificación es requerido.")
  private String ci;

  @NotBlank(message = "El nombre completo es requerido.")
  private String fullName;

  @NotBlank(message = "La dirección es requerida.")
  private String address;

  @NotBlank(message = "El teléfono es requerido.")
  private String phone;

  private Boolean indicatorExoneration;
  private String reasonExoneration;
  private String taxpayerStatus;

  @NotBlank(message = "La ciudad es requerida.")
  private String taxpayerCity;

  private String houseNumber;

  @NotNull(message = "El tipo de contribuyente es requerido.")
  private Integer taxpayerType;

  @NotNull(message = "El tipo de persona es requerido.")
  private Integer legalPerson;

  @NotNull(message = "El tipo de identificación es requerido..")
  private Integer identificationType;

  private LocalDate birthdate;

  @Min(value = 0, message = "El porcentaje de discapacidad no puede ser negativo")
  @Max(value = 100, message = "El porcentaje de discapacidad no puede ser mayor a 100")
  private Integer disabilityPercentage;

  @NotNull(message = "El estado civil es requerido.")
  private Integer maritalStatus;

  private MultipartFile ciImage;
  private MultipartFile faceImage;
  private boolean identityValidated;

  // Constructor
  public UserRegistrationDTO() {
  }

  public UserRegistrationDTO(
    String username, 
    String password, 
    String email,  
    boolean acceptedTerms,

    String ci,
    String fullName, 
    String address, 
    String phone,
    Boolean indicatorExoneration,   //no se pasa UserServiceImpl
    String reasonExoneration,       //no se pasa UserServiceImpl
    String taxpayerStatus,          //no se pasa UserServiceImpl
    String taxpayerCity,
    String houseNumber,
    Integer taxpayerType,
    Integer legalPerson,
    Integer identificationType,
    LocalDate birthdate,
    Integer disabilityPercentage,
    Integer maritalStatus
    ) {
    this.username = username;
    this.password = password;
    this.email = email;
    this.acceptedTerms = acceptedTerms;
    
    this.ci = ci;
    this.fullName = fullName;
    this.address = address;
    this.phone = phone;
    this.indicatorExoneration = indicatorExoneration;
    this.reasonExoneration = reasonExoneration;
    this.taxpayerStatus = taxpayerStatus;
    this.taxpayerCity = taxpayerCity;
    this.houseNumber = houseNumber;
    this.taxpayerType = taxpayerType;
    this.legalPerson = legalPerson;
    this.identificationType = identificationType;
    this.birthdate = birthdate;
    this.disabilityPercentage = disabilityPercentage;
    this.maritalStatus = maritalStatus;
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

  public MultipartFile getCiImage() {
    return ciImage;
  }

  public void setCiImage(MultipartFile ciImage) {
    this.ciImage = ciImage;
  }

  public MultipartFile getFaceImage() {
    return faceImage;
  }

  public void setFaceImage(MultipartFile faceImage) {
    this.faceImage = faceImage;
  }

  public boolean isIdentityValidated() {
    return identityValidated;
  }

  public void setIdentityValidated(boolean identityValidated) {
    this.identityValidated = identityValidated;
  }

}
