package com.azo.backend.msvc.users_prod.msvc_users_prod.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import com.azo.backend.msvc.users_prod.msvc_users_prod.models.entities.Contribuyente;

//2. Segundo Create Repository -> donde estan los métodos CRUD de SPRING BOOT
//CrudRepository ORM(JPA con hibernet) para realizar el CRUD, interacción directa con la DB

public interface ContribuyenteRepository extends CrudRepository<Contribuyente, String> {

  //método custom 2 formas

  Optional<Contribuyente> findByCi(String ci);

  //1ra. método custom para buscar por ci
  Optional<Contribuyente> findByTaxpayerStatus(String taxpayerStatus);

  //2do. método custom para paginación
  Page<Contribuyente> findAll(Pageable pageable);

  //3ro. ver si existe el documento de identidad
  boolean existsByCi(String ci);

}
