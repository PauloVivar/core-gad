package com.azo.backend.msvc.binnacle.msvc_binnacle.models.entities;


import java.time.LocalDateTime;
import java.util.List;

import com.azo.backend.msvc.binnacle.msvc_binnacle.enums.PaymentStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "payments")
public class Payment {

  @Id
  private String id;

  private String concept;
  private double value;
  private String reference;

  @Enumerated(EnumType.STRING)
  private PaymentStatus status;
  
  @OneToMany(mappedBy = "payment", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<CreditTitle> creditTitles;

  private LocalDateTime createdAt;
  private Long requestId;
  private String processUrl;
  private String ci;

  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }
  public String getConcept() {
    return concept;
  }
  public void setConcept(String concept) {
    this.concept = concept;
  }
  public double getValue() {
    return value;
  }
  public void setValue(double value) {
    this.value = value;
  }
  public String getReference() {
    return reference;
  }
  public void setReference(String reference) {
    this.reference = reference;
  }
  public PaymentStatus getStatus() {
    return status;
  }
  public void setStatus(PaymentStatus status) {
    this.status = status;
  }
  public List<CreditTitle> getCreditTitles() {
    return creditTitles;
  }
  public void setCreditTitles(List<CreditTitle> creditTitles) {
    this.creditTitles = creditTitles;
  }
  public LocalDateTime getCreatedAt() {
    return createdAt;
  }
  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }
  public Long getRequestId() {
    return requestId;
  }
  public void setRequestId(Long requestId) {
    this.requestId = requestId;
  }
  public String getProcessUrl() {
    return processUrl;
  }
  public void setProcessUrl(String processUrl) {
    this.processUrl = processUrl;
  }
  public String getCi() {
    return ci;
  }
  public void setCi(String ci) {
    this.ci = ci;
  }
  
}
