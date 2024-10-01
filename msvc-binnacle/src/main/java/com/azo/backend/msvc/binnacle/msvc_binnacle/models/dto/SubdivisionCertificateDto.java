package com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto;

public class SubdivisionCertificateDto {

  private Long id;
  private String details;
  private Long requestId;
  
  public SubdivisionCertificateDto() {
  }

  public SubdivisionCertificateDto(Long id, String details, Long requestId) {
    this.id = id;
    this.details = details;
    this.requestId = requestId;
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

  public Long getRequestId() {
    return requestId;
  }

  public void setRequestId(Long requestId) {
    this.requestId = requestId;
  }

}
