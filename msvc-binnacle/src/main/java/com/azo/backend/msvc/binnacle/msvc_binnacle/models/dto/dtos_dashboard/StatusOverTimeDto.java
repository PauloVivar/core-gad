package com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.dtos_dashboard;

import com.azo.backend.msvc.binnacle.msvc_binnacle.enums.RequestStatus;

public class StatusOverTimeDto {
  private RequestStatus status;
  private int year;
  private int month;
  private Long count;
  
  public StatusOverTimeDto(RequestStatus status, int year, int month, Long count) {
    this.status = status;
    this.year = year;
    this.month = month;
    this.count = count;
  }

  public RequestStatus getStatus() {
    return status;
  }
  public void setStatus(RequestStatus status) {
    this.status = status;
  }
  public int getYear() {
    return year;
  }
  public void setYear(int year) {
    this.year = year;
  }
  public int getMonth() {
    return month;
  }
  public void setMonth(int month) {
    this.month = month;
  }
  public Long getCount() {
    return count;
  }
  public void setCount(Long count) {
    this.count = count;
  }

}
