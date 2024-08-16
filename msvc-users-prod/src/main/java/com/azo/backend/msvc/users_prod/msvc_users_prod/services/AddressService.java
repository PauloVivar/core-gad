package com.azo.backend.msvc.users_prod.msvc_users_prod.services;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.azo.backend.msvc.users_prod.msvc_users_prod.models.dto.AddressDto;
import com.azo.backend.msvc.users_prod.msvc_users_prod.models.dto.CustomerDto;

//3. Tercero Create CustomService -> Implementación del CRUD
//Interacción con la tabla user_custom(dto) y cliente

public interface AddressService {

  //método custom para listar todo con paginación data users
  Page<CustomerDto> findAll(Pageable pageable);

  AddressDto findById(Long id);
  List<AddressDto> findAll();
  AddressDto update(Long id, AddressDto addressDto);
  void deleteById(Long id);

}
