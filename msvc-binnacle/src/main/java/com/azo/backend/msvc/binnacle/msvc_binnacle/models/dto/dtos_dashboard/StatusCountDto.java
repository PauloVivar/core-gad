package com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.dtos_dashboard;

import com.azo.backend.msvc.binnacle.msvc_binnacle.enums.RequestStatus;

public class StatusCountDto {

  private RequestStatus status;
  private Long count;

  public StatusCountDto() {
  }
  
  public StatusCountDto(RequestStatus status, Long count) {
    this.status = status;
    this.count = count;
  }

  public RequestStatus getStatus() {
    return status;
  }

  public void setStatus(RequestStatus status) {
    this.status = status;
  }

  public Long getCount() {
    return count;
  }

  public void setCount(Long count) {
    this.count = count;
  }

}
