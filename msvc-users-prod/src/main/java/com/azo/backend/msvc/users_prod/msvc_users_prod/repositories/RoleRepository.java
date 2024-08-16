package com.azo.backend.msvc.users_prod.msvc_users_prod.repositories;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import com.azo.backend.msvc.users_prod.msvc_users_prod.models.entities.Role;

//2. Segundo Create Repositorio -> donde estan los métodos CRUD de SPRING BOOT y metodos personalizados
//CrudRepository ORM(JPA con hibernet) para realizar el CRUD

public interface RoleRepository extends CrudRepository<Role, Long> {
  
  //nomenclatura de Spring boot -> obtenemos el ROLE copmpleto de la DB
  Optional<Role> findByName(String name);

}
