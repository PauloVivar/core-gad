package com.azo.backend.msvc.users_prod.msvc_users_prod.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.azo.backend.msvc.users_prod.msvc_users_prod.models.dto.ContribuyenteDto;
import com.azo.backend.msvc.users_prod.msvc_users_prod.models.entities.Contribuyente;

//3. Tercero Create ContribuyenteService -> Implementación del CRUD
//Interacción con la tabla user_custom(dto) y cliente

public interface ContribuyenteService {

  //listar data
  List<ContribuyenteDto> findAll();

  //listar data con paginación contribuyentes
  Page<ContribuyenteDto> findAll(Pageable pageable);

  //buscar data por ci
  Optional<ContribuyenteDto> findById(String ci);

  //guardar data
  ContribuyenteDto save(Contribuyente contribuyente);

  //actualizar data
  //Optional<UserDto> update (UserRequest user, Long id);
  Optional<ContribuyenteDto> update (Contribuyente contribuyente, String ci);

  //eliminar data
  void remove(String ci);

  //devuelve una lista por Ids
  //Iterable<ContribuyenteDto> findByAllId(String ci);

  //validar campo unique
  boolean existsByCi(String ci);

  //buscar por ci Document
  Optional<Contribuyente> findByTaxpayerStatus (String taxpayerStatus);
  
}
