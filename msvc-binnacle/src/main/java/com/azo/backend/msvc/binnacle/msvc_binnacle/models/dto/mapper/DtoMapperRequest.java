package com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.mapper;

import com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.RequestDto;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.entities.Request;

public class DtoMapperRequest {

  //Patron de diseño builder -> constructor que solo se puede intanciar con el metodo builder()

  private Request request;

  private DtoMapperRequest() {
  }

  public static DtoMapperRequest builder() {
      return new DtoMapperRequest();
  }

  public DtoMapperRequest setRequest(Request request) {
      this.request = request;
      return this;
  }

  public RequestDto build() {
      if(request == null) {
          throw new RuntimeException("Debe pasar el Entity Request!");
      }
      return new RequestDto(
          request.getId(),
          request.getEntryDate(),
          request.getStatus(),
          request.getCitizenId(),
          request.getCadastralCode(),
          request.getAssignedToUserId(), null
      );
  }
}
