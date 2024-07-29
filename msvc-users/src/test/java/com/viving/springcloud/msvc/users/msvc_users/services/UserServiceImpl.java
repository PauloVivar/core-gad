package com.viving.springcloud.msvc.users.msvc_users.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.viving.springcloud.msvc.users.msvc_users.models.entities.User;
import com.viving.springcloud.msvc.users.msvc_users.repositories.UserRepository;

//4. Se implementa UserService

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository repository;

  @Override
  @Transactional(readOnly = true)
  public List<User> findAll() {
    return (List<User>) repository.findAll();
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<User> findById(Long id) {
    return repository.findById(id);
  }

  @Override
  @Transactional
  public User save(User user) {
    return repository.save(user);
  }

  @Override
  @Transactional
  public void remove(Long id) {
    repository.deleteById(id);
  }

}
