package com.azo.backend.msvc.binnacle.msvc_binnacle.models.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "cadastral_records")
public class CadastralRecord {

  @Id
  @Column(name = "gcclacat")
  private String cadastralCode;

  @Column(name = "gcciudad")
  private String city;

  @Column(name = "gcprovnotaria")
  private String province;

  @Column(name = "gcpais")
  private String country;

  //Fecha de escritura
  @Column(name = "gcfecesc")
  private LocalDate deedDate;

  //Fecha de registro
  @Column(name = "gcfecreg")
  private LocalDate registrationDate;

  //Fecha de empadronamiento
  @Column(name = "gcfecemp")
  private LocalDate companyDate;

  //Fecha de última modificación
  @Column(name = "gcfecha")
  private LocalDate updateDate;
  
  //Zona patrimonial
  @Column(name = "gczonapatrimonial")
  private Integer heritageZone;
  
  @Column(name = "gcestado")
  private Integer status;

  public String getCadastralCode() {
    return cadastralCode;
  }

  public void setCadastralCode(String cadastralCode) {
    this.cadastralCode = cadastralCode;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getProvince() {
    return province;
  }

  public void setProvince(String province) {
    this.province = province;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public LocalDate getDeedDate() {
    return deedDate;
  }

  public void setDeedDate(LocalDate deedDate) {
    this.deedDate = deedDate;
  }

  public LocalDate getRegistrationDate() {
    return registrationDate;
  }

  public void setRegistrationDate(LocalDate registrationDate) {
    this.registrationDate = registrationDate;
  }

  public LocalDate getCompanyDate() {
    return companyDate;
  }

  public void setCompanyDate(LocalDate companyDate) {
    this.companyDate = companyDate;
  }

  public LocalDate getUpdateDate() {
    return updateDate;
  }

  public void setUpdateDate(LocalDate updateDate) {
    this.updateDate = updateDate;
  }

  public Integer getHeritageZone() {
    return heritageZone;
  }

  public void setHeritageZone(Integer heritageZone) {
    this.heritageZone = heritageZone;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

}
