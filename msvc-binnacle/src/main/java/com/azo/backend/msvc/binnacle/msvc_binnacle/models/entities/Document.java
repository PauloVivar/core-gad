package com.azo.backend.msvc.binnacle.msvc_binnacle.models.entities;

import java.time.LocalDateTime;

import com.azo.backend.msvc.binnacle.msvc_binnacle.enums.DocumentType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

//paso 1: entidad Documentos

@Entity
@Table(name = "documents")
public class Document {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  @NotBlank(message = "Los documentos son requeridos.")
  private DocumentType type;
  
  private LocalDateTime uploadDate;

  @NotEmpty
  private String fileUrl;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "request_id")
  private Request request;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public DocumentType getType() {
    return type;
  }

  public void setType(DocumentType type) {
    this.type = type;
  }

  public LocalDateTime getUploadDate() {
    return uploadDate;
  }

  public void setUploadDate(LocalDateTime uploadDate) {
    this.uploadDate = uploadDate;
  }

  public String getFileUrl() {
    return fileUrl;
  }

  public void setFileUrl(String fileUrl) {
    this.fileUrl = fileUrl;
  }

  public Request getRequestProcedure() {
    return request;
  }

  public void setRequestProcedure(Request request) {
    this.request = request;
  }

}
