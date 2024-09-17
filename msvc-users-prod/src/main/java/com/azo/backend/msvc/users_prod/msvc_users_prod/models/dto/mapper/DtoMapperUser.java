package com.azo.backend.msvc.users_prod.msvc_users_prod.models.dto.mapper;

import com.azo.backend.msvc.users_prod.msvc_users_prod.models.dto.ContribuyenteDto;
import com.azo.backend.msvc.users_prod.msvc_users_prod.models.dto.UserDetailDto;
import com.azo.backend.msvc.users_prod.msvc_users_prod.models.dto.UserDto;
import com.azo.backend.msvc.users_prod.msvc_users_prod.models.entities.User;

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

  //old
  // public UserDto build() {
  //   if(user == null){
  //     throw new RuntimeException("Debe pasar el Entity User!");
  //   }
  //   //role admin
  //   boolean isAdmin = user.getRoles().stream().anyMatch( r -> "ROLE_ADMIN".equals(r.getName()));
  //   return new UserDto(user.getId(), user.getUsername(), user.getEmail(), isAdmin, user.getAvatar(), user.getStatus());
  // }

  public UserDto build() {
    return buildUserDto(this.user);
  }

  public UserDetailDto buildDetail() {
    return buildUserDetailDto(this.user);
  }

  // Método estático para mantener compatibilidad con código existente
  public static UserDto build(User user) {
    return buildUserDto(user);
  }

  public static UserDetailDto buildDetail(User user) {
    return buildUserDetailDto(user);
  }

  public static UserDto buildUserDto(User user) {
    if (user == null) {
      throw new RuntimeException("Debe pasar el Entity User!");
    }

    UserDto userDto = new UserDto(
      user.getId(),
      user.getUsername(),
      user.getEmail(),
      user.getRoles().stream().anyMatch(r -> r.getName().equals("ROLE_ADMIN")),
      user.getAvatar(),
      user.getStatus()
    );

    // cédula del contribuyente si existe
    if (user.getContribuyente() != null) {
      userDto.setContribuyenteCi(user.getContribuyente().getCi());
    }

    return userDto;
  }

  private static UserDetailDto buildUserDetailDto(User user) {
    if (user == null) {
        throw new RuntimeException("Debe pasar el Entity User!");
    }

    String contribuyenteCi = user.getContribuyente() != null ? user.getContribuyente().getCi() : null;
    
    UserDetailDto userDetailDto = new UserDetailDto(
      user.getId(),
      user.getUsername(),
      user.getEmail(),
      user.getRoles().stream().anyMatch(r -> r.getName().equals("ROLE_ADMIN")),
      user.getAvatar(),
      user.getStatus(),
      contribuyenteCi
    );

    if (user.getContribuyente() != null) {
        ContribuyenteDto contribuyenteDto = new ContribuyenteDto(
            user.getContribuyente().getCi(),
            user.getContribuyente().getFullName(),
            user.getContribuyente().getAddress(),
            user.getContribuyente().getPhone(),

            user.getContribuyente().getIndicatorExoneration(),
            user.getContribuyente().getReasonExoneration(),
            user.getContribuyente().getTaxpayerStatus(),
            user.getContribuyente().getTaxpayerCity(),
            user.getContribuyente().getHouseNumber(),
            user.getContribuyente().getTaxpayerType(),
            
            user.getContribuyente().getLegalPerson(),
            user.getContribuyente().getIdentificationType(),
            user.getContribuyente().getBirthdate(),
            user.getContribuyente().getDisabilityPercentage(),
            user.getContribuyente().getMaritalStatus(),
            user.getId()
        );
        userDetailDto.setContribuyente(contribuyenteDto);
    }

    return userDetailDto;
  }

}
