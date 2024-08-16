package com.azo.backend.msvc.users_prod.msvc_users_prod.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import com.azo.backend.msvc.users_prod.msvc_users_prod.models.entities.Customer;

//2. Segundo Create Repository -> donde estan los métodos CRUD de SPRING BOOT
//CrudRepository ORM(JPA con hibernet) para realizar el CRUD, interacción directa con la DB

public interface CustomerRepository extends CrudRepository<Customer, Long> {

  //método custom 2 formas

  //1ra. método custom para buscar por documentId
  Optional<Customer> findByDocumentId(String documentId);

  //2do. método custom para paginación
  Page<Customer> findAll(Pageable pageable);

  //3ro. ver si existe el documento de identidad
  boolean existsByDocumentId(String documentId);

}
