package com.azo.backend.msvc.binnacle.msvc_binnacle.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.time.LocalDate;

import com.azo.backend.msvc.binnacle.msvc_binnacle.enums.SubdivisionType;

@Entity
@Table(name = "subdivision_certificates")
public class SubdivisionCertificate {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "details")
  private String details;

  @OneToOne
  @JoinColumn(name = "request_id")
  private Request request;

  @Enumerated(EnumType.STRING)
  @Column(name = "subdivision_type")
  private SubdivisionType subdivisionType;

  @Column(name = "ussue_date")
  private LocalDate issueDate;

  @Column(name = "file_url")
  private String fileUrl;

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

  public Request getRequest() {
    return request;
  }

  public void setRequest(Request request) {
    this.request = request;
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
