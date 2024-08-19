package com.azo.backend.msvc.users_prod.msvc_users_prod.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.azo.backend.msvc.users_prod.msvc_users_prod.models.dto.ContribuyenteDto;
import com.azo.backend.msvc.users_prod.msvc_users_prod.models.dto.mapper.DtoMapperContribuyente;
import com.azo.backend.msvc.users_prod.msvc_users_prod.models.entities.Contribuyente;
import com.azo.backend.msvc.users_prod.msvc_users_prod.models.entities.User;
import com.azo.backend.msvc.users_prod.msvc_users_prod.repositories.ContribuyenteRepository;
import com.azo.backend.msvc.users_prod.msvc_users_prod.repositories.UserRepository;

//4. Cuarto Implementación de ContribuyenteService -> volver realidad el CRUD

@Service
public class ContribuyenteServiceImpl implements ContribuyenteService {

  @Autowired
  private ContribuyenteRepository repository;

  @Autowired
  private UserRepository userRepository;

  @Override
  @Transactional(readOnly = true)
  public List<ContribuyenteDto> findAll() {
    List<Contribuyente> contribuyentes = (List<Contribuyente>) repository.findAll();
    return contribuyentes
      .stream()
      .map( c -> DtoMapperContribuyente.builder().setContribuyente(c).build())
      .collect(Collectors.toList());

    //return (List<Contribuyente>) repository.findAll();
  }

  //método custom para paginación
  @Override
  @Transactional(readOnly = true)
  public Page<ContribuyenteDto> findAll(Pageable pageable) {
    Page<Contribuyente> contribuyentesPage = repository.findAll(pageable);
    return contribuyentesPage.map(c -> DtoMapperContribuyente.builder().setContribuyente(c).build());
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<ContribuyenteDto> findById(String ci) {
    return repository.findById(ci).map(c -> DtoMapperContribuyente
      .builder()
      .setContribuyente(c)
      .build());
    
    //return repository.findById(ci);
  }

  //Transactional ya no es readOnly ya que save guarda y actualiza
  @Override
  @Transactional
  public ContribuyenteDto save(Contribuyente contribuyente) {

    return DtoMapperContribuyente.builder().setContribuyente(repository.save(contribuyente)).build();
    //return repository.save(contribuyente);
  }

  //update contribuyente
  @Override
  @Transactional
  public Optional<ContribuyenteDto> update(Contribuyente contribuyente, String ci) {
    Optional<Contribuyente> o = repository.findById(ci);
    //Contribuyente contribuyenteOptional = null;
    
    if(o.isPresent()){
      Contribuyente contribuyenteDb = o.orElseThrow();
      // Actualizar campos...
      contribuyenteDb.setFullName(contribuyente.getFullName());
      contribuyenteDb.setAddress(contribuyente.getAddress());
      contribuyenteDb.setPhone(contribuyente.getPhone());
      //contribuyenteDb.setContributionDate(contribuyente.getContributionDate());
      //contribuyenteDb.setResponsibleUser(contribuyente.getResponsibleUser());
      contribuyenteDb.setIndicatorExoneration(contribuyente.getIndicatorExoneration());
      contribuyenteDb.setReasonExoneration(contribuyente.getReasonExoneration());
      contribuyenteDb.setTaxpayerStatus(contribuyente.getTaxpayerStatus());
      contribuyenteDb.setTaxpayerCity(contribuyente.getTaxpayerCity());
      contribuyenteDb.setHouseNumber(contribuyente.getHouseNumber());
      contribuyenteDb.setTaxpayerType(contribuyente.getTaxpayerType());
      //contribuyenteDb.setContributionTime(contribuyente.getContributionTime());
      //contribuyenteDb.SetEstacionTrabajo(contribuyente.getWorkstation());
      //contribuyenteDb.setEmail(contribuyente.getEmail());
      contribuyenteDb.setLegalPerson(contribuyente.getLegalPerson());
      contribuyenteDb.setIdentificationType(contribuyente.getIdentificationType());
      contribuyenteDb.setBirthdate(contribuyente.getBirthdate());
      contribuyenteDb.setDisabilityPercentage(contribuyente.getDisabilityPercentage());
      contribuyenteDb.setMaritalStatus(contribuyente.getMaritalStatus());
      //contribuyenteDb.setSpouse(contribuyente.getSpouse());
      //contribuyenteDb.setPassword(contribuyente.getPassword());

      // Manejar la relación con User
      if (contribuyente.getUser() != null) {
        User user = userRepository.findById(contribuyente.getUser().getId())
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        contribuyenteDb.setUser(user);
        user.setContribuyente(contribuyenteDb);
      } else {
          // Si el contribuyente ya no tiene un usuario asociado
          if (contribuyenteDb.getUser() != null) {
            User oldUser = contribuyenteDb.getUser();
            oldUser.setContribuyente(null);
            userRepository.save(oldUser);
          }
          contribuyenteDb.setUser(null);
      }

      Contribuyente saved = repository.save(contribuyenteDb);
      return Optional.of(DtoMapperContribuyente.builder().setContribuyente(saved).build());

      //old
      //contribuyenteOptional = repository.save(contribuyenteDb);
    }

    return Optional.empty();
    //return Optional.ofNullable(DtoMapperContribuyente.builder().setContribuyente(contribuyenteOptional).build());
    //return Optional.ofNullable(contribuyenteOptional);
  }
  
  @Override
  @Transactional
  public void remove(String ci) {
    // eliminación el usuario
    repository.deleteById(ci);
  }

  @Override
  public boolean existsByCi(String ci) {
    return repository.existsByCi(ci);
  }

  @Override
  @Transactional
  public Optional<Contribuyente> findByTaxpayerStatus(String taxpayerStatus) {
    return repository.findByTaxpayerStatus(taxpayerStatus);
  }

}
