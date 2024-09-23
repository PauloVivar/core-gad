package com.azo.backend.msvc.binnacle.msvc_binnacle.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
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
  
 // método para contar solicitudes por estado
  long countByStatus(RequestStatus status);

  // método para contar todas las solicitudes agrupadas por estado
  @Query("SELECT r.status, COUNT(r) FROM Request r GROUP BY r.status")
  List<Object[]> countTotalRequestsByStatus();
}
