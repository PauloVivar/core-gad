package com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto;

import java.time.LocalDate;

public class CadastralRecordDto {

  private String cadastralCode;
  private String city;
  private String province;
  private String country;
  private LocalDate deedDate;
  private LocalDate registrationDate;
  private LocalDate companyDate;
  private LocalDate updateDate;
  private Integer heritageZone;
  private Integer status;

  public CadastralRecordDto() {
  }

  public CadastralRecordDto(String cadastralCode, String city, String province, String country, LocalDate deedDate,
      LocalDate registrationDate, LocalDate companyDate, LocalDate updateDate, Integer heritageZone, Integer status) {
    this.cadastralCode = cadastralCode;
    this.city = city;
    this.province = province;
    this.country = country;
    this.deedDate = deedDate;
    this.registrationDate = registrationDate;
    this.companyDate = companyDate;
    this.updateDate = updateDate;
    this.heritageZone = heritageZone;
    this.status = status;
  }

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
