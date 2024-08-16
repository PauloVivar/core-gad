package com.azo.backend.msvc.users_prod.msvc_users_prod.models.entities;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

//paso 1: entidad Contribuyente

@Entity
@Table(name = "contribuyentes")
public class Contribuyente {

  @Id
  @Column(name = "ctrcedula", length = 15)
  private String ci;

  @NotBlank(message = "El nombre completo es requerido.")
  @Column(name = "ctrnombre", length = 100)
  private String fullName;

  @Column(name = "ctrdireccion", length = 100)
  private String address;

  @Column(name = "ctrtelefono", columnDefinition = "text")
  private String phone;

  @Column(name = "ctrfecha")
  private LocalDate contributionDate;

  @Column(name = "ctrusuario", length = 60)
  private String responsibleUser;

  @Column(name = "ctrexonera")
  private Boolean indicatorExoneration;

  //omitido en Dto
  @Column(name = "ctrrazonex", columnDefinition = "text")
  private String reasonExoneration;

  @Column(name = "ctrestado", length = 20)
  private String taxpayerStatus;

  //omitido en Dto cedula Contribuyente Real
  @Column(name = "ctrcedulare", length = 15)
  private String realTaxpayerCi;

  @Column(name = "ctrciudad", length = 50)
  private String taxpayerCity;

  @Column(name = "ctrnumero", length = 10)
  private String houseNumber;

  @Column(name = "ctrtipo")
  private Integer taxpayerType;

  @Column(name = "ctrhora")
  private LocalTime contributionTime;

  //estacion de trabajo
  @Column(name = "ctresttra", length = 30)
  private String workstation;

  //omitido en Dto tipoProgramaContabilidad
  @Column(name = "cttipopro", length = 100)
  private String typeProgramAccounting;

  //omitido en Dto
  @Email(message = "Debe ser una dirección de correo válida")
  @Column(name = "ctremail", length = 120)
  private String email;

  @Column(name = "ctrpersoneria")
  private Integer legalPerson;

  @Column(name = "ctrtipoid")
  private Integer identificationType;

  //omitido en Dto
  @Column(name = "ctrfechanacimiento")
  private LocalDate birthdate;

  @Column(name = "ctrdiscapacidad")
  private Integer disabilityPercentage;

  @Column(name = "ctrcivil")
  private Integer maritalStatus;

  //omitido en Dto spouse
  @Column(name = "ctrcontrayente", length = 13)
  private String spouse;

  @Column(name = "ctrpassword", length = 15)
  private String password;

  // Relación OneToOne hereda de Entity User
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", unique = true, nullable = true)
  private User user;

  // Constructor por defecto
  public Contribuyente() {}

  // Getters y Setters
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

  public LocalDate getContributionDate() {
    return contributionDate;
  }

  public void setContributionDate(LocalDate contributionDate) {
    this.contributionDate = contributionDate;
  }

  public String getResponsibleUser() {
    return responsibleUser;
  }

  public void setResponsibleUser(String responsibleUser) {
    this.responsibleUser = responsibleUser;
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

  public String getRealTaxpayerCi() {
    return realTaxpayerCi;
  }

  public void setRealTaxpayerCi(String realTaxpayerCi) {
    this.realTaxpayerCi = realTaxpayerCi;
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

  public LocalTime getContributionTime() {
    return contributionTime;
  }

  public void setContributionTime(LocalTime contributionTime) {
    this.contributionTime = contributionTime;
  }

  public String getWorkstation() {
    return workstation;
  }

  public void setWorkstation(String workstation) {
    this.workstation = workstation;
  }

  public String getTypeProgramAccounting() {
    return typeProgramAccounting;
  }

  public void setTypeProgramAccounting(String typeProgramAccounting) {
    this.typeProgramAccounting = typeProgramAccounting;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
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

  public String getSpouse() {
    return spouse;
  }

  public void setSpouse(String spouse) {
    this.spouse = spouse;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

}
