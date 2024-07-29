package com.azo.backend.msvc.users.msvc_users.repositories;

import org.springframework.data.repository.CrudRepository;
import com.azo.backend.msvc.users.msvc_users.models.entities.User;


//2. Se crea el Repositorio para el CRUD
public interface UserRepository extends CrudRepository<User, Long> {

}
