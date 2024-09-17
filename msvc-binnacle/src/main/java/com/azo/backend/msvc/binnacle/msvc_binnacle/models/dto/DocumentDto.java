package com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto;

import com.azo.backend.msvc.binnacle.msvc_binnacle.enums.DocumentType;
import java.time.LocalDateTime;

public class DocumentDto {
  private Long id;
  private DocumentType type;
  private LocalDateTime uploadDate;
  private String fileUrl;

  public DocumentDto() {}

  public DocumentDto(Long id, DocumentType type, LocalDateTime uploadDate, String fileUrl) {
    this.id = id;
    this.type = type;
    this.uploadDate = uploadDate;
    this.fileUrl = fileUrl;
  }

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

}
