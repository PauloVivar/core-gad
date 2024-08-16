package com.azo.backend.msvc.users_prod.msvc_users_prod.models.dto.mapper;

import com.azo.backend.msvc.users_prod.msvc_users_prod.models.dto.CustomerDto;
import com.azo.backend.msvc.users_prod.msvc_users_prod.models.entities.Customer;

public class DtoMapperCustomer {

  //Patron de diseño builder -> constructor que solo se puede intanciar con el metodo builder()

  private Customer customer;

  private DtoMapperCustomer() {
  }

  public static DtoMapperCustomer builder() {
    return new DtoMapperCustomer();
  }

  public DtoMapperCustomer setCustomer(Customer customer) {
    this.customer = customer;
    return this;
  }

  public CustomerDto build() {
    if(customer == null){
      throw new RuntimeException("Debe pasar el Entity Customer!");
    }
    
    //return new CustomerDto(customer.getId(), customer.getUsername(), customer.getEmail(), isAdmin);
    return new CustomerDto(customer.getId(), customer.getFirstname(), customer.getLastname(), customer.getDocumentId(), customer.getTypeDocumentId(), customer.getUser().getId());
  }

}
