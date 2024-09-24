package com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.dtos_dashboard;

public class AssignedUserCountDto {

  private Long assignedToUserId;
  private Long count;
  
  public AssignedUserCountDto() {
  }
  public AssignedUserCountDto(Long assignedToUserId, Long count) {
    this.assignedToUserId = assignedToUserId;
    this.count = count;
  }

  public Long getAssignedToUserId() {
    return assignedToUserId;
  }
  public void setAssignedToUserId(Long assignedToUserId) {
    this.assignedToUserId = assignedToUserId;
  }
  public Long getCount() {
    return count;
  }
  public void setCount(Long count) {
    this.count = count;
  }

}
