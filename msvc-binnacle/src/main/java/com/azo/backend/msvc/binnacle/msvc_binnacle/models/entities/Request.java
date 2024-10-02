package com.azo.backend.msvc.binnacle.msvc_binnacle.models.entities;

import java.time.LocalDateTime;
import java.util.List;

import com.azo.backend.msvc.binnacle.msvc_binnacle.enums.RequestStatus;
import com.azo.backend.msvc.binnacle.msvc_binnacle.enums.RequestType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

//paso 1: entidad Solicitudes
//patrones

@Entity
@Table(name = "requests")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "request_type", discriminatorType = DiscriminatorType.STRING)
public abstract class Request {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  protected Long id;
  
  @NotEmpty
  @Column(name = "entry_date", nullable = false)
  protected LocalDateTime entryDate;
  
  @Column(name = "end_date")
  protected LocalDateTime endDate;

  @Enumerated(EnumType.STRING)
  @NotBlank(message = "El tipo trámite es requerido.")
  @Column(nullable = false)
  protected RequestType type;
  
  @Enumerated(EnumType.STRING)
  @NotBlank(message = "El estado es requerido.")
  @Column(nullable = false)
  protected RequestStatus status;

  @NotBlank(message = "El ciudadano es requerido.")
  @Column(nullable = false)
  protected Long citizenId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "cadastral_code", referencedColumnName = "gcclacat")
  private CadastralRecord cadastralRecord;

  @Column(nullable = true)
  protected Long assignedToUserId;

  @OneToMany(mappedBy = "request", cascade = CascadeType.ALL, orphanRemoval = true)
  @NotBlank(message = "Los documentos son requeridos.")
  protected List<Document> documents;

  @PrePersist
  protected void onCreate() {
    entryDate = LocalDateTime.now();
  }
  
  public abstract void process();

  //Getters and Setters

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

  public RequestType getType() {
    return type;
  }

  public void setType(RequestType type) {
    this.type = type;
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

  public CadastralRecord getCadastralRecord() {
    return cadastralRecord;
  }

  public void setCadastralRecord(CadastralRecord cadastralRecord) {
    this.cadastralRecord = cadastralRecord;
  }

}