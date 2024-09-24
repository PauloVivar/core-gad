package com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.dtos_dashboard;

import java.util.Map;

import com.azo.backend.msvc.binnacle.msvc_binnacle.enums.RequestStatus;

public class DashboardSummaryDto {
  private Long totalRequests;
  private Map<RequestStatus, Long> requestCountByStatus;
  private Double averageResolutionTime;
  private Long pendingRequests;
  
  public Long getTotalRequests() {
    return totalRequests;
  }
  public void setTotalRequests(Long totalRequests) {
    this.totalRequests = totalRequests;
  }
  public Map<RequestStatus, Long> getRequestCountByStatus() {
    return requestCountByStatus;
  }
  public void setRequestCountByStatus(Map<RequestStatus, Long> requestCountByStatus) {
    this.requestCountByStatus = requestCountByStatus;
  }
  public Double getAverageResolutionTime() {
    return averageResolutionTime;
  }
  public void setAverageResolutionTime(Double averageResolutionTime) {
    this.averageResolutionTime = averageResolutionTime;
  }
  public Long getPendingRequests() {
    return pendingRequests;
  }
  public void setPendingRequests(Long pendingRequests) {
    this.pendingRequests = pendingRequests;
  }
  
}
