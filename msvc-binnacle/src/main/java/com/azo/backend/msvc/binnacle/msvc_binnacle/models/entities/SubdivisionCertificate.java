package com.azo.backend.msvc.binnacle.msvc_binnacle.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "subdivision_certificates")
public class SubdivisionCertificate {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "details")
  private String details;

  @OneToOne
  @JoinColumn(name = "cadastral_record_id")
  private CadastralRecord cadastralRecord;

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

  public CadastralRecord getCadastralRecord() {
    return cadastralRecord;
  }

  public void setCadastralRecord(CadastralRecord cadastralRecord) {
    this.cadastralRecord = cadastralRecord;
  }
  
}
