package com.azo.backend.msvc.avaluos_catastros.msvc_avaluos_catastros.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.azo.backend.msvc.avaluos_catastros.msvc_avaluos_catastros.models.entities.Procedure;

//2. Segundo Create Repositorio -> donde estan los métodos CRUD de SPRING BOOT
//CrudRepository ORM(JPA con hibernet) para realizar el CRUD, interacción directa con la DB
public interface ProcedureRepository extends CrudRepository<Procedure, Long> {

  //1ra. método custom para buscar por tip de trámite
  Optional<Procedure> findByTypeProcedure(String typeProcedure);

  //2do. método custom para paginación
  Page<Procedure> findAll(Pageable pageable);

}
