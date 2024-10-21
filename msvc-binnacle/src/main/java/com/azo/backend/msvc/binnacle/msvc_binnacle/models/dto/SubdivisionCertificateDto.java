package com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto;

import java.time.LocalDate;

import com.azo.backend.msvc.binnacle.msvc_binnacle.enums.SubdivisionType;

public class SubdivisionCertificateDto {

  private Long id;
  private String details;
  private Long requestId;
  private SubdivisionType subdivisionType;
  private LocalDate issueDate;
  private String fileUrl;
  
  
  public SubdivisionCertificateDto() {
  }

  public SubdivisionCertificateDto(Long id, String details, Long requestId, SubdivisionType subdivisionType, LocalDate issueDate, String fileUrl) {
    this.id = id;
    this.details = details;
    this.requestId = requestId;
    this.subdivisionType = subdivisionType;
    this.issueDate = issueDate;
    this.fileUrl = fileUrl;
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

  public LocalDate getIssueDate() {
    return issueDate;
  }

  public void setIssueDate(LocalDate issueDate) {
    this.issueDate = issueDate;
  }

  public String getFileUrl() {
    return fileUrl;
  }

  public void setFileUrl(String fileUrl) {
    this.fileUrl = fileUrl;
  }

  public SubdivisionType getSubdivisionType() {
    return subdivisionType;
  }

  public void setSubdivisionType(SubdivisionType subdivisionType) {
    this.subdivisionType = subdivisionType;
  }

}
