package com.azo.backend.msvc.users_bck.msvc_users_bck.models.dto.mapper;

import java.util.stream.Collectors;

import com.azo.backend.msvc.users_bck.msvc_users_bck.models.dto.AddressDto;
import com.azo.backend.msvc.users_bck.msvc_users_bck.models.dto.CustomerDto;
import com.azo.backend.msvc.users_bck.msvc_users_bck.models.dto.UserDetailDto;
import com.azo.backend.msvc.users_bck.msvc_users_bck.models.dto.UserDto;
import com.azo.backend.msvc.users_bck.msvc_users_bck.models.entities.Address;
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

  //old
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

    return userDto;
  }

  private static UserDetailDto buildUserDetailDto(User user) {
    if (user == null) {
        throw new RuntimeException("Debe pasar el Entity User!");
    }
    
    UserDetailDto userDetailDto = new UserDetailDto(
        user.getId(),
        user.getUsername(),
        user.getEmail(),
        user.getRoles().stream().anyMatch(r -> r.getName().equals("ROLE_ADMIN")),
        user.getAvatar(),
        user.getStatus()
    );

    if (user.getCustomer() != null) {
        CustomerDto customerDto = new CustomerDto(
            user.getCustomer().getId(),
            user.getCustomer().getFirstname(),
            user.getCustomer().getLastname(),
            user.getCustomer().getDocumentId(),
            user.getCustomer().getTypeDocumentId(),
            user.getId(),
            user.getCustomer().getAddresses().stream()
                    .map(DtoMapperUser::mapAddress)
                    .collect(Collectors.toList())
        );
        userDetailDto.setCustomer(customerDto);
    }

    return userDetailDto;
  }

  private static AddressDto mapAddress(Address address) {
    return new AddressDto(
      address.getId(),
      address.getStreet(),
      address.getCity(),
      address.getProvince(),
      address.getPostalCode(),
      address.getCountry()
    );
  }
}
