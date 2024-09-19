package com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.mapper;

import java.util.stream.Collectors;

import com.azo.backend.msvc.binnacle.msvc_binnacle.models.User;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.RequestDetailDto;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.RequestDto;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.entities.Request;

public class DtoMapperRequest {

  //Patron de diseño builder -> constructor que solo se puede intanciar con el metodo builder()

  private Request request;
  private User citizen;
  private User assignedUser;

  private DtoMapperRequest() {
  }

  public static DtoMapperRequest builder() {
      return new DtoMapperRequest();
  }

  public DtoMapperRequest setRequest(Request request) {
      this.request = request;
      return this;
  }

  public DtoMapperRequest setCitizen(User citizen) {
    this.citizen = citizen;
    return this;
  }

  public DtoMapperRequest setAssignedUser(User assignedUser) {
    this.assignedUser = assignedUser;
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
      request.getAssignedToUserId(),
      request.getDocuments() != null ? 
            request.getDocuments().stream()
                .map(doc -> DtoMapperDocument.builder().setDocument(doc).build())
                .collect(Collectors.toList()) : 
            null
    );
  }

  public RequestDetailDto buildDetailDto() {
    if(request == null) {
        throw new RuntimeException("Debe pasar el Entity RequestDetail!");
    }
    RequestDetailDto dto = new RequestDetailDto(
      request.getId(),
      request.getEntryDate(),
      request.getStatus(),
      request.getCitizenId(),
      request.getCadastralCode(),
      request.getAssignedToUserId(),
      request.getDocuments() != null ? 
            request.getDocuments().stream()
                .map(doc -> DtoMapperDocument.builder().setDocument(doc).build())
                .collect(Collectors.toList()) : 
            null
    );

    if (citizen != null) {
      dto.setCitizenName(citizen.getUsername());
      dto.setCitizenEmail(citizen.getEmail());
    }

    if (assignedUser != null) {
      dto.setAssignedToUserName(assignedUser.getUsername());
      dto.setAssignedToUserEmail(assignedUser.getEmail());
    }

    if (request.getDocuments() != null) {
      dto.setDocuments(request.getDocuments().stream()
          .map(doc -> DtoMapperDocument.builder().setDocument(doc).build())
          .collect(Collectors.toList()));
    }

    return dto;
  }
}