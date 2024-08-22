package com.azo.backend.msvc.users_prod.msvc_users_prod.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.azo.backend.msvc.users_prod.msvc_users_prod.models.dto.UserDetailDto;
import com.azo.backend.msvc.users_prod.msvc_users_prod.models.dto.UserDto;
import com.azo.backend.msvc.users_prod.msvc_users_prod.models.dto.UserRegistrationDTO;
import com.azo.backend.msvc.users_prod.msvc_users_prod.models.entities.User;
import com.azo.backend.msvc.users_prod.msvc_users_prod.models.request.UserRequest;

//3. Tercero Create UserService -> Implementación del CRUD
//Interacción con la tabla user_custom(dto) y cliente

public interface UserService {

  //listar todos los users
  List<UserDto> findAll();

  //listar todos los users con paginación
  Page<UserDto> findAll(Pageable pageable);

  //buscar users por id
  Optional<UserDetailDto> findById(Long id);

  //guardar user
  UserDto save(User user);

  //guardar user registration
  UserDto saveRegistration(UserRegistrationDTO userRegistration, String ipAddress);

  //actualizar user
  Optional<UserDto> update (UserRequest user, Long id);
  //Optional<UserDto> update (UserRegistrationDTO user, Long id);

  //eliminar user
  void remove(Long id);

  //*test activo y desactivo status
  // UserDto activateUser(Long id);
  // UserDto deactivateUser(Long id);

  //validar campos unique
  boolean existsByUsername(String username);
  boolean existsByEmail(String email);

  //reset password
  //método para buscar user por email
  Optional<User> findByEmail(String email);

  //métodos para el restablecimiento de contraseña
  String createPasswordResetCodeForUser(User user);
  String validatePasswordResetCode(String code);
  User getUserByPasswordResetCode(String code);
  void changeUserPassword(User user, String newPassword);

  boolean isContribuyenteAssociated(String ci);
  
}
