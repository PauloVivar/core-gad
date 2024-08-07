package com.azo.backend.msvc.users_bck.msvc_users_bck.models.request;

import com.azo.backend.msvc.users_bck.msvc_users_bck.models.IUser;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

//1.1 Create Class UserRequest personalización de JSON

public class UserRequest implements IUser {

  @NotBlank(message = "El username es requerido.")
  @Column(unique = true)
  private String username;

  @NotEmpty(message = "El email es requerido.")
  @Email(message = "Ingrese un email válido.")
  @Column(unique = true)
  private String email;

  //Role admin
  private boolean admin;

  private String avatar;

  @Pattern(regexp = "ACTIVO|INACTIVO", message = "El estado debe ser ACTIVO o INACTIVO")
  @Column(columnDefinition = "VARCHAR(10) DEFAULT 'ACTIVO'")
  private String status;

  //Getters and Setters
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

  @Override
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

}
