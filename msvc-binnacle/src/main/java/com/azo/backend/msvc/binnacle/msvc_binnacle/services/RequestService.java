package com.azo.backend.msvc.binnacle.msvc_binnacle.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.azo.backend.msvc.binnacle.msvc_binnacle.enums.RequestStatus;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.RequestDto;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.entities.Request;

//3. Tercero Create RequestService -> Implementación del CRUD
//Interacción con la tabla user(dto) y cliente
public interface RequestService {
  //listar todos los users
  List<RequestDto> findAll();

  //listar todos los users con paginación
  Page<RequestDto> findAll(Pageable pageable);

  //listar solicitudes por id
  //RequestDto findById(Long id);
  Optional<RequestDto> findById(Long id);

  //crear solicitud
  RequestDto save(Request request);

  //actualizar solicitud
  RequestDto update(Long id, Request request);

  //eliminar solicitud
  void remove(Long id);

  List<RequestDto> getRequestsByStatus(RequestStatus status);
  List<RequestDto> getRequestsByUser(Long userId);

}
