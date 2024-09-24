package com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.dtos_dashboard;

import java.util.List;

public class DashboardStatisticsDto {
  
  private List<StatusOverTimeDto> requestsByStatusOverTime;
  private List<TypeCountDto> requestsByType;
  private List<UserCountDto> requestsByUser;
  private List<AssignedUserCountDto> requestsByAssignedUser;

  public List<StatusOverTimeDto> getRequestsByStatusOverTime() {
    return requestsByStatusOverTime;
  }
  public void setRequestsByStatusOverTime(List<StatusOverTimeDto> requestsByStatusOverTime) {
    this.requestsByStatusOverTime = requestsByStatusOverTime;
  }
  public List<TypeCountDto> getRequestsByType() {
    return requestsByType;
  }
  public void setRequestsByType(List<TypeCountDto> requestsByType) {
    this.requestsByType = requestsByType;
  }
  public List<UserCountDto> getRequestsByUser() {
    return requestsByUser;
  }
  public void setRequestsByUser(List<UserCountDto> requestsByUser) {
    this.requestsByUser = requestsByUser;
  }
  public List<AssignedUserCountDto> getRequestsByAssignedUser() {
    return requestsByAssignedUser;
  }
  public void setRequestsByAssignedUser(List<AssignedUserCountDto> requestsByAssignedUser) {
    this.requestsByAssignedUser = requestsByAssignedUser;
  }

}
