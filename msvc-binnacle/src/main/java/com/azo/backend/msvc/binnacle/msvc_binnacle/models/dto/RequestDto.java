package com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto;

import java.util.List;
import java.time.LocalDateTime;

import com.azo.backend.msvc.binnacle.msvc_binnacle.enums.RequestStatus;
import com.azo.backend.msvc.binnacle.msvc_binnacle.enums.RequestType;

public class RequestDto {
  private Long id;
  private LocalDateTime entryDate;
  private RequestStatus status;
  private RequestType type;
  private Long citizenId;
  private String cadastralCode;
  private Long assignedToUserId;
  private List<DocumentDto> documents;

  public RequestDto() {
  }

  public RequestDto(
      Long id, 
      LocalDateTime entryDate,
      RequestStatus status,
      RequestType type,
      Long citizenId,
      String cadastralCode, 
      Long assignedToUserId,
      List<DocumentDto> documents
    ) {

      this.id = id;
      this.entryDate = entryDate;
      this.status = status;
      this.type = type;
      this.citizenId = citizenId;
      this.cadastralCode = cadastralCode;
      this.assignedToUserId = assignedToUserId;
      this.documents = documents;
  }

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

  public List<DocumentDto> getDocuments() {
    return documents;
  }

  public void setDocuments(List<DocumentDto> documents) {
    this.documents = documents;
  }

  public RequestType getType() {
    return type;
  }

  public void setType(RequestType type) {
    this.type = type;
  }
  
}