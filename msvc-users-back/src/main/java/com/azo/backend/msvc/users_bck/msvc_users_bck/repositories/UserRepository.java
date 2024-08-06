package com.azo.backend.msvc.users_bck.msvc_users_bck.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.azo.backend.msvc.users_bck.msvc_users_bck.models.entities.User;

//2. Segundo Create Repositorio -> donde estan los métodos CRUD de SPRING BOOT
//CrudRepository ORM(JPA con hibernet) para realizar el CRUD, interacción directa con la DB

public interface UserRepository extends CrudRepository<User, Long> {

  //método custom 2 formas

  //1ra. forma 1 -> siguiendo la nomenclatura de Spring boot, buscar por username
  Optional<User> findByUsername(String username);

  //1ra. forma 2 -> consulta personalizanda (nombre del método) -> "and u.email=?2" -> en caso de 2do parametro
  @Query("select u from User u where u.username = ?1")
  Optional<User> getUserByUsername(String username);

  //2ro. método custom para obtener id de user para mandar por el token
  @Query("select u.id from User u where u.username = ?1")
  Optional<Long> findIdByUsername(String username);

  //3ro. método custom para paginación
  Page<User> findAll(Pageable pageable);

  //4to. método custom para findByEmail
  Optional<User> findByEmail(String email);

}
