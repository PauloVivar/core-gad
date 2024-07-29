package com.viving.springcloud.msvc.users.msvc_users.services;

import java.util.List;
import java.util.Optional;

import com.viving.springcloud.msvc.users.msvc_users.models.entities.User;

//3. crear UserSevice
public interface UserService {
  List<User> findAll();
  Optional<User> findById(Long id);
  User save (User user);
  void remove(Long id);

}
