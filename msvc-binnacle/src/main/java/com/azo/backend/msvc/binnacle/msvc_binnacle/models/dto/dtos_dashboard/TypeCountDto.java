package com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.dtos_dashboard;

import com.azo.backend.msvc.binnacle.msvc_binnacle.enums.RequestType;

public class TypeCountDto {
  private RequestType type;
  private Long count;

  public TypeCountDto() {
  }

  public TypeCountDto(RequestType type, Long count) {
    this.type = type;
    this.count = count;
  }

  public RequestType getType() {
    return type;
  }

  public void setType(RequestType type) {
    this.type = type;
  }

  public Long getCount() {
    return count;
  }

  public void setCount(Long count) {
    this.count = count;
  }

}
