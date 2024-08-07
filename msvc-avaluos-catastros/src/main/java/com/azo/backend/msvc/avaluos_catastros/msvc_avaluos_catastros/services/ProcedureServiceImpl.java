package com.azo.backend.msvc.avaluos_catastros.msvc_avaluos_catastros.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.azo.backend.msvc.avaluos_catastros.msvc_avaluos_catastros.models.Customer;
import com.azo.backend.msvc.avaluos_catastros.msvc_avaluos_catastros.models.entities.Procedure;
import com.azo.backend.msvc.avaluos_catastros.msvc_avaluos_catastros.repositories.ProcedureRepository;

//paso 4.
@Service
public class ProcedureServiceImpl implements ProcedureService {

  @Autowired
  private ProcedureRepository repository;

  @Override
  @Transactional(readOnly = true)
  public List<Procedure> findAll() {
    return (List<Procedure>) repository.findAll();
  }

  @Override
  @Transactional(readOnly = true)
  public Page<Procedure> findAll(Pageable pageable) {
    return repository.findAll(pageable);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<Procedure> findById(Long id) {
    return repository.findById(id);
  }

  @Override
  @Transactional
  public Procedure save(Procedure procedure) {
    return repository.save(procedure);
  }

  // @Override
  // @Transactional
  // public Optional<Procedure> update(Procedure procedure, Long id) {
  //   Optional<Procedure> o = repository.findById(id);
  //   Procedure procedureOptional = null;
  //   if(o.isPresent()){
  //     Procedure procedureDb = o.orElseThrow();
  //     procedureDb.setStatus(procedure.getStatus());
  //     procedureDb.setTypeProcedure(procedure.getTypeProcedure());
  //     procedureDb.setStartDate(procedure.getStartDate());
  //     procedureDb.setEndDate(procedure.getEndDate());
  //     procedureOptional = repository.save(procedureDb);
  //   }
  //   return Optional.ofNullable(procedureOptional);
  // }

  @Override
  @Transactional
  public void remove(Long id) {
    repository.deleteById(id);
  }

  @Override
  public Optional<Customer> assignCustomer(Customer customer, Long procedureId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'assignCustomer'");
  }

  @Override
  public Optional<Customer> createCustomer(Customer customer, Long procedureId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'createCustomer'");
  }

  @Override
  public Optional<Customer> removeCustomer(Customer customer, Long procedureId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'removeCustomer'");
  }

}
