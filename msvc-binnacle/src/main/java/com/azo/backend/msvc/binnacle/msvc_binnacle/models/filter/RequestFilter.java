package com.azo.backend.msvc.binnacle.msvc_binnacle.models.filter;

import java.time.LocalDate;

import com.azo.backend.msvc.binnacle.msvc_binnacle.enums.RequestStatus;

public class RequestFilter {
  
  private RequestStatus status;
  private String cadastralCode;
  private LocalDate entryDate;
  private LocalDate endDate;
  private Long userId;
  
  public RequestStatus getStatus() {
    return status;
  }
  public void setStatus(RequestStatus status) {
    this.status = status;
  }
  public String getCadastralCode() {
    return cadastralCode;
  }
  public void setCadastralCode(String cadastralCode) {
    this.cadastralCode = cadastralCode;
  }
  public LocalDate getEntryDate() {
    return entryDate;
  }
  public void setEntryDate(LocalDate entryDate) {
    this.entryDate = entryDate;
  }
  public LocalDate getEndDate() {
    return endDate;
  }
  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }
  public Long getUserId() {
    return userId;
  }
  public void setUserId(Long userId) {
    this.userId = userId;
  }
  
  
  
}
