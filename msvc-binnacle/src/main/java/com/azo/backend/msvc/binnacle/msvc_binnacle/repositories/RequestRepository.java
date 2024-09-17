package com.azo.backend.msvc.binnacle.msvc_binnacle.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.azo.backend.msvc.binnacle.msvc_binnacle.enums.RequestStatus;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.entities.Request;

//2. Segundo Create Repositorio -> donde estan los métodos CRUD de SPRING BOOT
//CrudRepository ORM(JPA con hibernet) para realizar el CRUD, interacción directa con la DB
public interface RequestRepository extends CrudRepository<Request, Long> {

  //método custom para paginación
  Page<Request> findAll(Pageable pageable);

  List<Request> findByStatus(RequestStatus status);
  List<Request> findByCitizenId(Long citizenId);
  
  //cuando se elimina un user de msvc_users se desasigna ese user de la solicitud
  // @Modifying
  // @Query("delete from RequestUser ru where ru.userId=?1")
  // void removeRequestUserById(Long id);
}
