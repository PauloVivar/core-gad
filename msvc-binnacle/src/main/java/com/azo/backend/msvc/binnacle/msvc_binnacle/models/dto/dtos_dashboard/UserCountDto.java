package com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.dtos_dashboard;

public class UserCountDto {
  private Long userId;
  private Long count;
  
  public UserCountDto() {
  }

  public UserCountDto(Long userId, Long count) {
    this.userId = userId;
    this.count = count;
  }

  public Long getUserId() {
    return userId;
  }
  public void setUserId(Long userId) {
    this.userId = userId;
  }
  public Long getCount() {
    return count;
  }
  public void setCount(Long count) {
    this.count = count;
  }

}
