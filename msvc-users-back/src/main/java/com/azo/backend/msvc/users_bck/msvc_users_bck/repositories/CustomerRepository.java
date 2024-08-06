package com.azo.backend.msvc.users_bck.msvc_users_bck.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import com.azo.backend.msvc.users_bck.msvc_users_bck.models.entities.Customer;

//2. Segundo Create Repository -> donde estan los métodos CRUD de SPRING BOOT
//CrudRepository ORM(JPA con hibernet) para realizar el CRUD, interacción directa con la DB

public interface CustomerRepository extends CrudRepository<Customer, Long> {

  //método custom 2 formas

  //1ra. método custom para buscar por documentId
  Optional<Customer> findByIdDocument(String documentId);

  //2do. método custom para paginación
  Page<Customer> findAll(Pageable pageable);

}
