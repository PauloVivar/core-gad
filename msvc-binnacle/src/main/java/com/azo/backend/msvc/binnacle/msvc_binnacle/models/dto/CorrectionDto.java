package com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto;

import java.time.LocalDateTime;

import com.azo.backend.msvc.binnacle.msvc_binnacle.enums.CorrectionStatus;

public class CorrectionDto {
  private Long id;
  private Long requestId;
  private String description;
  private LocalDateTime dateNotified;
  private LocalDateTime deadline;
  private CorrectionStatus status;

  public CorrectionDto() {
  }

  public CorrectionDto(Long id, Long requestId, String description, LocalDateTime dateNotified, LocalDateTime deadline, CorrectionStatus status) {
    this.id = id;
    this.requestId = requestId;
    this.description = description;
    this.dateNotified = dateNotified;
    this.deadline = deadline;
    this.status = status;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getRequestId() {
    return requestId;
  }

  public void setRequestId(Long requestId) {
    this.requestId = requestId;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public LocalDateTime getDateNotified() {
    return dateNotified;
  }

  public void setDateNotified(LocalDateTime dateNotified) {
    this.dateNotified = dateNotified;
  }

  public LocalDateTime getDeadline() {
    return deadline;
  }

  public void setDeadline(LocalDateTime deadline) {
    this.deadline = deadline;
  }

  public CorrectionStatus getStatus() {
    return status;
  }

  public void setStatus(CorrectionStatus status) {
    this.status = status;
  }
  
}
