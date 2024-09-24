package com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.dtos_dashboard;

public class TypeCountDto {
  private String type;
  private Long count;

  public TypeCountDto() {
  }

  public TypeCountDto(String type, Long count) {
    this.type = type;
    this.count = count;
  }

  public String getType() {
    return type;
  }
  public void setType(String type) {
    this.type = type;
  }
  public Long getCount() {
    return count;
  }
  public void setCount(Long count) {
    this.count = count;
  }
}
