package com.azo.backend.msvc.avaluos_catastros.msvc_avaluos_catastros.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.azo.backend.msvc.avaluos_catastros.msvc_avaluos_catastros.models.Customer;
import com.azo.backend.msvc.avaluos_catastros.msvc_avaluos_catastros.models.entities.Procedure;

//3. Tercero Create ProcedureService -> Implementación del CRUD
//Interacción con la tabla user_custom(dto) y cliente
public interface ProcedureService {
  //listar todos los users
  List<Procedure> findAll();

  //listar todos los users con paginación
  Page<Procedure> findAll(Pageable pageable);

  //buscar users por id
  Optional<Procedure> findById(Long id);

  //guardar procedure
  Procedure save(Procedure procedure);

  //actualizar procedure
  //Optional<Procedure> update (Procedure procedure, Long id);

  //eliminar procedure
  void remove(Long id);

  //agregar customer de microservios msvc-users-back a Tramite(Procedure)
  Optional<Customer> assignCustomer(Customer customer, Long procedureId);
  Optional<Customer> createCustomer(Customer customer, Long procedureId);
    //no eliminamos si no desasignamos
  Optional<Customer> removeCustomer(Customer customer, Long procedureId);

  //validar campos unique
  //boolean existsByUsername(String username);
  //boolean existsByEmail(String email);

}
