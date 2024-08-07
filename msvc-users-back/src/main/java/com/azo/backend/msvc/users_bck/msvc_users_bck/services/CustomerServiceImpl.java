package com.azo.backend.msvc.users_bck.msvc_users_bck.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.azo.backend.msvc.users_bck.msvc_users_bck.models.dto.CustomerDto;
import com.azo.backend.msvc.users_bck.msvc_users_bck.models.dto.mapper.DtoMapperCustomer;
import com.azo.backend.msvc.users_bck.msvc_users_bck.models.entities.Customer;
import com.azo.backend.msvc.users_bck.msvc_users_bck.repositories.CustomerRepository;

//4. Cuarto Implementación de CustomerService -> volver realidad el CRUD

@Service
public class CustomerServiceImpl implements CustomerService {

  @Autowired
  private CustomerRepository repository;

  @Override
  @Transactional(readOnly = true)
  public List<CustomerDto> findAll() {
    List<Customer> customers = (List<Customer>) repository.findAll();
    return customers
      .stream()
      .map( c -> DtoMapperCustomer.builder().setCustomer(c).build())
      .collect(Collectors.toList());

    //return (List<Customer>) repository.findAll();
  }

  //método custom para paginación
  @Override
  @Transactional(readOnly = true)
  public Page<CustomerDto> findAll(Pageable pageable) {
    Page<Customer> customersPage = repository.findAll(pageable);
    return customersPage.map(c -> DtoMapperCustomer.builder().setCustomer(c).build());
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<CustomerDto> findById(Long id) {
    return repository.findById(id).map(c -> DtoMapperCustomer
      .builder()
      .setCustomer(c)
      .build());
    
    //return repository.findById(id);
  }

  //Transactional ya no es readOnly ya que save guarda y actualiza
  @Override
  @Transactional
  public CustomerDto save(Customer customer) {

    return DtoMapperCustomer.builder().setCustomer(repository.save(customer)).build();
    //return repository.save(customer);
  }

  //Se utiliza UserRequest ya que no se pasa el password
  @Override
  @Transactional
  public Optional<CustomerDto> update(Customer customer, Long id) {
    Optional<Customer> o = repository.findById(id);
    Customer customerOptional = null;
    if(o.isPresent()){

      Customer customerDb = o.orElseThrow();
      customerDb.setFirstname(customer.getFirstname());
      customerDb.setLastname(customer.getLastname());
      customerDb.setDocumentId(customer.getDocumentId());
      customerDb.setTypeDocumentId(customer.getTypeDocumentId());
      customerOptional = repository.save(customerDb);
    }
    return Optional.ofNullable(DtoMapperCustomer.builder().setCustomer(customerOptional).build());
    //return Optional.ofNullable(customerOptional);
  }
  
  @Override
  @Transactional
  public void remove(Long id) {
    // Primero, eliminamos todas las aceptaciones de términos asociadas
    //termsAcceptanceRepository.deleteByUserId(id);
    // Luego, eliminamos el usuario
    repository.deleteById(id);
  }

  @Override
  public boolean existsByDocumentId(String documentId) {
    return repository.toString().equals(documentId);
  }

  @Override
  @Transactional
  public Optional<Customer> findByDocumentId(String documentId) {
    return repository.findByDocumentId(documentId);
  }

}
