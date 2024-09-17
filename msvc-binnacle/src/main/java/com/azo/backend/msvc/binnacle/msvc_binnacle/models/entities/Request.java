package com.azo.backend.msvc.binnacle.msvc_binnacle.models.entities;

import java.time.LocalDateTime;
import java.util.List;

import com.azo.backend.msvc.binnacle.msvc_binnacle.enums.RequestStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

//paso 1: entidad Solicitudes

@Entity
@Table(name = "requests")
public class Request {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @NotBlank(message = "El entryDate es requerido.")
  @Column(name = "start_date", nullable = false)
  private LocalDateTime entryDate;
  
  @Column(name = "end_date")
  private LocalDateTime endDate;
  
  @Enumerated(EnumType.STRING)
  @NotBlank(message = "El status es requerido.")
  @Column(nullable = false)
  private RequestStatus status;

  @PrePersist
  protected void onCreate() {
    entryDate = LocalDateTime.now();
  }

  @NotBlank(message = "El ciudadano es requerido.")
  @Column(nullable = false)
  private Long citizenId;

  @NotBlank(message = "La clave catastral es requerida.")
  private String cadastralCode;

  @NotBlank(message = "El Técnico es requerido.")
  @Column(nullable = false)
  private Long assignedToUserId;

  @OneToMany(mappedBy = "request", cascade = CascadeType.ALL, orphanRemoval = true)
  @NotBlank(message = "Los documentos son requeridos.")
  private List<Document> documents;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public LocalDateTime getEntryDate() {
    return entryDate;
  }

  public void setEntryDate(LocalDateTime entryDate) {
    this.entryDate = entryDate;
  }

  public LocalDateTime getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDateTime endDate) {
    this.endDate = endDate;
  }

  public RequestStatus getStatus() {
    return status;
  }

  public void setStatus(RequestStatus status) {
    this.status = status;
  }

  public Long getCitizenId() {
    return citizenId;
  }

  public void setCitizenId(Long citizenId) {
    this.citizenId = citizenId;
  }

  public String getCadastralCode() {
    return cadastralCode;
  }

  public void setCadastralCode(String cadastralCode) {
    this.cadastralCode = cadastralCode;
  }

  public Long getAssignedToUserId() {
    return assignedToUserId;
  }

  public void setAssignedToUserId(Long assignedToUserId) {
    this.assignedToUserId = assignedToUserId;
  }

  public List<Document> getDocuments() {
    return documents;
  }

  public void setDocuments(List<Document> documents) {
    this.documents = documents;
  }

}