package com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto;

public class SubdivisionCertificateDto {

  private Long id;
  private String details;
  private String cadastralRecordCode;
  
  public SubdivisionCertificateDto() {
  }

  public SubdivisionCertificateDto(Long id, String details, String cadastralRecordCode) {
    this.id = id;
    this.details = details;
    this.cadastralRecordCode = cadastralRecordCode;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getDetails() {
    return details;
  }

  public void setDetails(String details) {
    this.details = details;
  }

  public String getCadastralRecordCode() {
    return cadastralRecordCode;
  }

  public void setCadastralRecordCode(String cadastralRecordCode) {
    this.cadastralRecordCode = cadastralRecordCode;
  }

  
  
}
