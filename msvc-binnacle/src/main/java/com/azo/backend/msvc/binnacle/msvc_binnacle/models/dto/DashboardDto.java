package com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto;

import java.util.Map;

import com.azo.backend.msvc.binnacle.msvc_binnacle.enums.RequestStatus;

public class DashboardDto {
  private Map<RequestStatus, Long> requestCountByStatus;
  private Long totalRequests;
  private Long pendingRequests;

  // Constructores

  public DashboardDto() {}

  public DashboardDto(Map<RequestStatus, Long> requestCountByStatus, Long totalRequests, Long pendingRequests) {
    this.requestCountByStatus = requestCountByStatus;
    this.totalRequests = totalRequests;
    this.pendingRequests = pendingRequests;
  }

  public Map<RequestStatus, Long> getRequestCountByStatus() {
    return requestCountByStatus;
  }

  public void setRequestCountByStatus(Map<RequestStatus, Long> requestCountByStatus) {
    this.requestCountByStatus = requestCountByStatus;
  }

  public Long getTotalRequests() {
    return totalRequests;
  }

  public void setTotalRequests(Long totalRequests) {
    this.totalRequests = totalRequests;
  }

  public Long getPendingRequests() {
    return pendingRequests;
  }

  public void setPendingRequests(Long pendingRequests) {
    this.pendingRequests = pendingRequests;
  }

}
