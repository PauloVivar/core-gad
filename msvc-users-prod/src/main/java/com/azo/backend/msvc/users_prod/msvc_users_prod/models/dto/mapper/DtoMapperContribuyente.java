package com.azo.backend.msvc.users_prod.msvc_users_prod.models.dto.mapper;

import com.azo.backend.msvc.users_prod.msvc_users_prod.models.dto.ContribuyenteDto;
import com.azo.backend.msvc.users_prod.msvc_users_prod.models.entities.Contribuyente;

public class DtoMapperContribuyente {

  //Patron de diseño builder -> constructor que solo se puede intanciar con el metodo builder()

  private Contribuyente contribuyente;

  private DtoMapperContribuyente() {
  }

  public static DtoMapperContribuyente builder() {
    return new DtoMapperContribuyente();
  }

  public DtoMapperContribuyente setContribuyente(Contribuyente contribuyente) {
    this.contribuyente = contribuyente;
    return this;
  }

  public ContribuyenteDto build() {
    if(contribuyente == null){
      throw new RuntimeException("Debe pasar el Entity Contribuyente!");
    }
    
    //return new ContribuyenteDto(contribuyente.getId(), contribuyente.getUsername(), contribuyente.getEmail(), isAdmin);
    return new ContribuyenteDto(
      contribuyente.getCi(), 
      contribuyente.getFullName(), 
      contribuyente.getAddress(), 
      contribuyente.getPhone(), 
      //contribuyente.getContributionDate(), 
      //contribuyente.getResponsibleUser(),
      contribuyente.getIndicatorExoneration(),
      contribuyente.getReasonExoneration(),
      contribuyente.getTaxpayerStatus(),
      contribuyente.getTaxpayerCity(),
      contribuyente.getHouseNumber(),
      contribuyente.getTaxpayerType(),
      //contribuyente.getContributionTime(),
      //contribuyente.getWorkstation(),
      //contribuyente.getEmail(),
      contribuyente.getLegalPerson(),
      contribuyente.getIdentificationType(),
      contribuyente.getBirthdate(),
      contribuyente.getDisabilityPercentage(),
      contribuyente.getMaritalStatus(),
      //contribuyente.getSpouse(),
      //contribuyente.getPassword(),
      contribuyente.getUser().getId());
  }

}
