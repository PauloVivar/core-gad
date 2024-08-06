package com.azo.backend.msvc.users_bck.msvc_users_bck.models.dto.mapper;

import com.azo.backend.msvc.users_bck.msvc_users_bck.models.dto.CustomerDto;
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

  // public UserDto build() {
  //   if(user == null){
  //     throw new RuntimeException("Debe pasar el Entity User!");
  //   }
  //   //role admin
  //   boolean isAdmin = user.getRoles().stream().anyMatch( r -> "ROLE_ADMIN".equals(r.getName()));
  //   return new UserDto(user.getId(), user.getUsername(), user.getEmail(), isAdmin, user.getAvatar(), user.getStatus());
  // }

  //test
  public UserDto build() {
    return buildUserDto(this.user);
  }

  // Método estático para mantener compatibilidad con código existente
  public static UserDto build(User user) {
      return buildUserDto(user);
  }

  public static UserDto buildUserDto(User user) {
    UserDto userDto = new UserDto(
      user.getId(),
      user.getUsername(),
      user.getEmail(),
      user.getRoles().stream().anyMatch(r -> r.getName().equals("ROLE_ADMIN")),
      user.getAvatar(),
      user.getStatus(), null
    );

    if(user.getCustomer() != null) {
      CustomerDto customerDto = new CustomerDto(
          user.getCustomer().getId(),
          user.getCustomer().getFirstname(),
          user.getCustomer().getLastname(),
          user.getCustomer().getDocumentId(),
          user.getCustomer().getTypeDocumentId(),
          user.getId()  // Establecemos el userId en CustomerDto
      );
      userDto.setCustomer(customerDto);
    }

    return userDto;
  }
}
