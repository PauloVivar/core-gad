package com.azo.backend.msvc.users_bck.msvc_users_bck.models.dto;

public class UserDetailDto extends UserDto {

  private CustomerDto customer;

  public UserDetailDto() {
  }
  
  public UserDetailDto(Long id, String username, String email, boolean admin, String avatar, String status) {
    super(id, username, email, admin, avatar, status);
  }

  //Getters and Setters

  public CustomerDto getCustomer() {
    return customer;
  }

  public void setCustomer(CustomerDto customer) {
    this.customer = customer;
  }

}
