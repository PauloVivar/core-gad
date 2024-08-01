package com.azo.backend.msvc.courses.msvc_courses.models;

//import java.time.LocalDateTime;

public class User {

  private Long id;
  private String username;
  private String password;
  private String email;
  private String cellphone;
  private String phone;
  private String avatar;
  //private LocalDateTime createdAt;

  //getters and setters

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
  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
  }
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  public String getCellphone() {
    return cellphone;
  }
  public void setCellphone(String cellphone) {
    this.cellphone = cellphone;
  }
  public String getPhone() {
    return phone;
  }
  public void setPhone(String phone) {
    this.phone = phone;
  }
  public String getAvatar() {
    return avatar;
  }
  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }
  // public LocalDateTime getCreatedAt() {
  //   return createdAt;
  // }
  // public void setCreatedAt(LocalDateTime createdAt) {
  //   this.createdAt = createdAt;
  // }
  
}
