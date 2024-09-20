package com.azo.backend.msvc.binnacle.msvc_binnacle.repositories;

import java.util.List;

//import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.azo.backend.msvc.binnacle.msvc_binnacle.models.entities.Document;

//2. Segundo Create Repositorio -> donde estan los métodos CRUD de SPRING BOOT
//CrudRepository ORM(JPA con hibernet) para realizar el CRUD, interacción directa con la DB
public interface DocumentRepository extends CrudRepository<Document, Long> {

  //método custom para paginación
  List<Document> findAllByRequestId(Long requestId);
  Page<Document> findAllPageByRequestId(Long requestId, Pageable pageable);

}
