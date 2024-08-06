package com.azo.backend.msvc.users_bck.msvc_users_bck.models.dto;

public class UserDto {
  private Long id;
  private String username;
  private String email;
  private boolean admin;     //role admin
  private String avatar;
  private String status;
  private CustomerDto customer;

  public UserDto() {
  }

  public UserDto(Long id, String username, String email, boolean admin, String avatar, String status, CustomerDto customer) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.admin = admin;
    this.avatar = avatar;
    this.status = status;
    this.customer = customer;
  }
  
  //Getters and Setters
  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  public String getUsername() {
    return username;
  }
  public void setUsername(String username) {
    this.username = username;
  }
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  
  public boolean isAdmin() {
    return admin;
  }
  public void setAdmin(boolean admin) {
    this.admin = admin;
  }

  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public CustomerDto getCustomer() {
    return customer;
  }

  public void setCustomer(CustomerDto customer) {
    this.customer = customer;
  }

}
