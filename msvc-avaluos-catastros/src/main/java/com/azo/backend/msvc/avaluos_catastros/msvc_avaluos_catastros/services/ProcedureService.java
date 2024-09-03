package com.azo.backend.msvc.avaluos_catastros.msvc_avaluos_catastros.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.azo.backend.msvc.avaluos_catastros.msvc_avaluos_catastros.models.User;
import com.azo.backend.msvc.avaluos_catastros.msvc_avaluos_catastros.models.entities.Procedure;

//3. Tercero Create ProcedureService -> Implementación del CRUD
//Interacción con la tabla user_custom(dto) y cliente
public interface ProcedureService {
  //listar todos los users
  List<Procedure> findAll();

  //listar todos los users con paginación
  Page<Procedure> findAll(Pageable pageable);

  //buscar tramites por id
  Optional<Procedure> findById(Long id);

  //buscar tramites con usuarios por id
  Optional<Procedure> findByIdWithUsers(Long id);

  //guardar procedure
  Procedure save(Procedure procedure);

  //actualizar procedure
  //Optional<Procedure> update (Procedure procedure, Long id);

  //eliminar procedure
  void remove(Long id);

  //cuando se elimina un user de msvc_users se desasigna ese user del trámite
  void removeProcedureUserById(Long id);

  //agregar user de microservios msvc-users-prod a Tramite(Procedure)
  Optional<User> assignUser(User user, Long procedureId);
  Optional<User> createUser(User user, Long procedureId);        //crera un nuevo user desde el msvvc_users
  Optional<User> removeUser(User user, Long procedureId);        //no eliminamos si no desasignamos

}
