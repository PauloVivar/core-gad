package com.azo.backend.msvc.users_prod.msvc_users_prod.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.azo.backend.msvc.users_prod.msvc_users_prod.models.dto.AddressDto;
import com.azo.backend.msvc.users_prod.msvc_users_prod.models.dto.CustomerDto;
import com.azo.backend.msvc.users_prod.msvc_users_prod.models.entities.Customer;

//3. Tercero Create CustomService -> Implementación del CRUD
//Interacción con la tabla user_custom(dto) y cliente

public interface CustomerService {

  //listar data
  List<CustomerDto> findAll();

  //método custom para listar todo con paginación data users
  Page<CustomerDto> findAll(Pageable pageable);

  //buscar data por id
  Optional<CustomerDto> findById(Long id);

  //guardar data
  CustomerDto save(Customer customer);

  //actualizar data
  //Optional<UserDto> update (UserRequest customer, Long id);
  Optional<CustomerDto> update (Customer customer, Long id);

  //eliminar data
  void remove(Long id);

  //devuelve una lista por Ids
  //Iterable<CustomerDto> findByAllId(Long id);

  //validar campo unique
  boolean existsByDocumentId(String documentId);

  //buscar por id Document
  Optional<Customer> findByDocumentId (String documentId);

  // Nuevos métodos para manejar direcciones
  CustomerDto addAddressToCustomer(Long customerId, AddressDto addressDto);
  CustomerDto removeAddressFromCustomer(Long customerId, Long addressId);
  List<AddressDto> getCustomerAddresses(Long customerId);
  
}
