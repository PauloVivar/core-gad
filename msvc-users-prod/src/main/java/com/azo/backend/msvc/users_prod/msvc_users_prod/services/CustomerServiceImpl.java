package com.azo.backend.msvc.users_prod.msvc_users_prod.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.azo.backend.msvc.users_prod.msvc_users_prod.models.dto.AddressDto;
import com.azo.backend.msvc.users_prod.msvc_users_prod.models.dto.CustomerDto;
import com.azo.backend.msvc.users_prod.msvc_users_prod.models.dto.mapper.DtoMapperCustomer;
import com.azo.backend.msvc.users_prod.msvc_users_prod.models.entities.Address;
import com.azo.backend.msvc.users_prod.msvc_users_prod.models.entities.Customer;
import com.azo.backend.msvc.users_prod.msvc_users_prod.repositories.AddressRepository;
import com.azo.backend.msvc.users_prod.msvc_users_prod.repositories.CustomerRepository;

//4. Cuarto Implementación de CustomerService -> volver realidad el CRUD

@Service
public class CustomerServiceImpl implements CustomerService {

  @Autowired
  private CustomerRepository repository;

  @Autowired
  private AddressRepository addressRepository;

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
    return repository.existsByDocumentId(documentId);
  }

  @Override
  @Transactional
  public Optional<Customer> findByDocumentId(String documentId) {
    return repository.findByDocumentId(documentId);
  }


  //se asocia un Address a un Customer
  @Override
  @Transactional
  public CustomerDto addAddressToCustomer(Long customerId, AddressDto addressDto) {
      Customer customer = repository.findById(customerId)
          .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

      Address address = convertToEntity(addressDto);
      address.setCustomer(customer);
      customer.addAddress(address);

      repository.save(customer);
      return convertToDto(customer);
  }

  @Override
  @Transactional
  public CustomerDto removeAddressFromCustomer(Long customerId, Long addressId) {
    Customer customer = repository.findById(customerId)
        .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

    Address address = addressRepository.findById(addressId)
        .orElseThrow(() -> new RuntimeException("Dirección no encontrada"));

    customer.removeAddress(address);
    repository.save(customer);
    addressRepository.delete(address);

    return convertToDto(customer);
  }

  @Override
  @Transactional
  public List<AddressDto> getCustomerAddresses(Long customerId) {
      Customer customer = repository.findById(customerId)
          .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

      return customer.getAddresses().stream()
          .map(this::convertAddressToDto)
          .collect(Collectors.toList());
  }


  //metodos utils
  private Address convertToEntity(AddressDto dto) {
    Address address = new Address();
    address.setStreet(dto.getStreet());
    address.setCity(dto.getCity());
    address.setProvince(dto.getProvince());
    address.setPostalCode(dto.getPostalCode());
    address.setCountry(dto.getCountry());
    return address;
  }

  private CustomerDto convertToDto(Customer customer) {
    CustomerDto dto = new CustomerDto();
    dto.setId(customer.getId());
    dto.setFirstname(customer.getFirstname());
    dto.setLastname(customer.getLastname());
    dto.setDocumentId(customer.getDocumentId());
    dto.setTypeDocumentId(customer.getTypeDocumentId());
    if (customer.getUser() != null) {
        dto.setUserId(customer.getUser().getId());
    }
    
    // Convertir y establecer las direcciones si existen
    if (customer.getAddresses() != null) {
        dto.setAddresses(customer.getAddresses().stream()
            .map(this::convertAddressToDto)
            .collect(Collectors.toList()));
    }
    
    return dto;
  }

  private AddressDto convertAddressToDto(Address address) {
    AddressDto dto = new AddressDto();
    dto.setId(address.getId());
    dto.setStreet(address.getStreet());
    dto.setCity(address.getCity());
    dto.setProvince(address.getProvince());
    dto.setPostalCode(address.getPostalCode());
    dto.setCountry(address.getCountry());
    return dto;
  }

}
