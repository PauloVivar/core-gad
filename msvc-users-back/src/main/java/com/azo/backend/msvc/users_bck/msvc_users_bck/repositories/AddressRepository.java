package com.azo.backend.msvc.users_bck.msvc_users_bck.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import com.azo.backend.msvc.users_bck.msvc_users_bck.models.entities.Address;

//2. Segundo Create Repository -> donde estan los métodos CRUD de SPRING BOOT
//CrudRepository ORM(JPA con hibernet) para realizar el CRUD, interacción directa con la DB

public interface AddressRepository extends CrudRepository<Address, Long> {

  //métodos custom

  //1ro. método custom para paginación
  Page<Address> findAll(Pageable pageable);

}
