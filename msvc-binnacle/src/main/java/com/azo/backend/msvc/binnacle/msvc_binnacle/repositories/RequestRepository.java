package com.azo.backend.msvc.binnacle.msvc_binnacle.repositories;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.azo.backend.msvc.binnacle.msvc_binnacle.enums.RequestStatus;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.dtos_dashboard.AssignedUserCountDto;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.dtos_dashboard.StatusOverTimeDto;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.dtos_dashboard.TypeCountDto;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.dtos_dashboard.UserCountDto;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.entities.Request;

//2. Segundo Create Repositorio -> donde estan los métodos CRUD de SPRING BOOT
//CrudRepository ORM(JPA con hibernet) para realizar el CRUD, interacción directa con la DB
public interface RequestRepository extends JpaRepository<Request, Long>, JpaSpecificationExecutor<Request>  {

  //método custom para paginación
  //Page<Request> findAll(Pageable pageable);

  List<Request> findByStatus(RequestStatus status);
  List<Request> findByCitizenId(Long citizenId);

  List<Request> findByAssignedToUserId(Long assignedToUserId);
  
  // método para contar solicitudes por estado
  long countByStatus(RequestStatus status);

  // método para contar todas las solicitudes agrupadas por estado
  @Query("SELECT r.status, COUNT(r) FROM Request r GROUP BY r.status")
  List<Object[]> countTotalRequestsByStatus();

  // métodos para Dashboard
  //Representa el conteo de solicitudes por cada estado, agrupa los resultados por el estado de la solicitud.
  @Query("SELECT new com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.dtos_dashboard.StatusCountDto(r.status, COUNT(r)) FROM Request r GROUP BY r.status")
  Map<RequestStatus, Long> countByStatus();

  //Calcula el promedio de días entre la fecha de entrada y la fecha de finalización para las solicitudes aprobadas.
  @Query("SELECT AVG(DATEDIFF(r.endDate, r.entryDate)) FROM Request r WHERE r.status = 'APROBADO'")
  Double calculateAverageResolutionTime();

  //Esta consulta agrupa las solicitudes por estado, año y mes de la fecha de entrada
  @Query("SELECT new com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.dtos_dashboard.StatusOverTimeDto(r.status, FUNCTION('YEAR', r.entryDate), FUNCTION('MONTH', r.entryDate), COUNT(r)) " +
          "FROM Request r GROUP BY r.status, FUNCTION('YEAR', r.entryDate), FUNCTION('MONTH', r.entryDate)")
  List<StatusOverTimeDto> getRequestsByStatusOverTime();

  //Esta consulta cuenta las solicitudes agrupándolas por su tipo.
  @Query("SELECT new com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.dtos_dashboard.TypeCountDto(r.type, COUNT(r)) FROM Request r GROUP BY r.type")
  List<TypeCountDto> countByType();

  //Esta consulta cuenta las solicitudes por usuario (ciudadano)
  @Query("SELECT new com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.dtos_dashboard.UserCountDto(r.citizenId, COUNT(r)) FROM Request r GROUP BY r.citizenId")
  List<UserCountDto> countByUser();

  //Esta consulta cuenta las solicitudes por usuario (revisor o técnico)
  @Query("SELECT new com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.dtos_dashboard.AssignedUserCountDto(r.assignedToUserId, COUNT(r)) FROM Request r WHERE r.assignedToUserId IS NOT NULL GROUP BY r.assignedToUserId")
  List<AssignedUserCountDto> countByAssignedUser();

}
