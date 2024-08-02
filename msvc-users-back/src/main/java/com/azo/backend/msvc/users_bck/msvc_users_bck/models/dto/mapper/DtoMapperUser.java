package com.azo.backend.msvc.users_bck.msvc_users_bck.models.dto.mapper;

import com.azo.backend.msvc.users_bck.msvc_users_bck.models.dto.UserDto;
import com.azo.backend.msvc.users_bck.msvc_users_bck.models.entities.User;

public class DtoMapperUser {

  //Patron de diseño builder -> constructor que solo se puede intanciar con el metodo builder()

  private User user;

  private DtoMapperUser() {
  }

  public static DtoMapperUser builder() {
    return new DtoMapperUser();
  }

  public DtoMapperUser setUser(User user) {
    this.user = user;
    return this;
  }

  public UserDto build() {
    if(user == null){
      throw new RuntimeException("Debe pasar el Entity User!");
    }

    //role admin
    boolean isAdmin = user.getRoles().stream().anyMatch( r -> "ROLE_ADMIN".equals(r.getName()));
    
    return new UserDto(user.getId(), user.getUsername(), user.getEmail(), isAdmin);
  }

}
