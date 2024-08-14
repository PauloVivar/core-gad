package com.azo.backend.msvc.users_bck.msvc_users_bck.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.azo.backend.msvc.users_bck.msvc_users_bck.models.dto.AddressDto;
import com.azo.backend.msvc.users_bck.msvc_users_bck.models.dto.CustomerDto;
import com.azo.backend.msvc.users_bck.msvc_users_bck.models.entities.Address;
import com.azo.backend.msvc.users_bck.msvc_users_bck.repositories.AddressRepository;

//4. Cuarto Implementación de AddressService -> volver realidad el CRUD

@Service
public class AddressServiceImpl implements AddressService {

  @Autowired
  private AddressRepository addressRepository;

  @Override
  public AddressDto findById(Long id) {
    Address address = addressRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Dirección no encontrada"));
    return convertToDto(address);
  }

  @Override
  public Page<CustomerDto> findAll(Pageable pageable) {
    throw new UnsupportedOperationException("Unimplemented method 'findAll'");
  }

  @Override
  public List<AddressDto> findAll() {
    return ((List<Address>) addressRepository.findAll())
        .stream()
        .map(this::convertToDto)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public AddressDto update(Long id, AddressDto addressDto) {
    Address address = addressRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Dirección no encontrada"));

    address.setStreet(addressDto.getStreet());
    address.setCity(addressDto.getCity());
    address.setProvince(addressDto.getProvince());
    address.setPostalCode(addressDto.getPostalCode());
    address.setCountry(addressDto.getCountry());

    addressRepository.save(address);
    return convertToDto(address);
  }

  @Override
  @Transactional
  public void deleteById(Long id) {
    addressRepository.deleteById(id);
  }

  private AddressDto convertToDto(Address address) {
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
