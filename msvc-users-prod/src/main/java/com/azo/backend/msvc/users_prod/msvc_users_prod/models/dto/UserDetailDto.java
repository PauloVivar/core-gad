package com.azo.backend.msvc.users_prod.msvc_users_prod.models.dto;

public class UserDetailDto extends UserDto {

  private ContribuyenteDto contribuyente;

  public UserDetailDto() {
  }
  
  public UserDetailDto(Long id, String username, String email, boolean admin, String avatar, String status, String contribuyenteCi) {
    super(id, username, email, admin, avatar, status);
    setContribuyenteCi(contribuyenteCi); //metodo de UserDto
  }

  //Getters and Setters

  public ContribuyenteDto getContribuyente() {
    return contribuyente;
  }

  public void setContribuyente(ContribuyenteDto contribuyente) {
    this.contribuyente = contribuyente;
  }

}
