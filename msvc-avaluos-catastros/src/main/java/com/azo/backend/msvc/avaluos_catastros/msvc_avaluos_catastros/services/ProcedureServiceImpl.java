package com.azo.backend.msvc.avaluos_catastros.msvc_avaluos_catastros.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.azo.backend.msvc.avaluos_catastros.msvc_avaluos_catastros.clients.CustomerClientRest;
import com.azo.backend.msvc.avaluos_catastros.msvc_avaluos_catastros.models.Customer;
import com.azo.backend.msvc.avaluos_catastros.msvc_avaluos_catastros.models.entities.Procedure;
import com.azo.backend.msvc.avaluos_catastros.msvc_avaluos_catastros.models.entities.ProcedureCustomer;
import com.azo.backend.msvc.avaluos_catastros.msvc_avaluos_catastros.repositories.ProcedureRepository;

//paso 4.
@Service
public class ProcedureServiceImpl implements ProcedureService {

  @Autowired
  private ProcedureRepository repository;

  @Autowired
  private CustomerClientRest client;

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

  //asignar usuario a tramite
  @Override
  @Transactional
  public Optional<Customer> assignCustomer(Customer customer, Long procedureId) {

    Optional<Procedure> o = repository.findById(procedureId);
    if(o.isPresent()){
      Customer customerMsvc = client.detail(customer.getId());

      Procedure procedure = o.get();
      ProcedureCustomer procedureCustomer = new ProcedureCustomer();
      procedureCustomer.setId(customerMsvc.getId());

      procedure.addProcedureCustomer(procedureCustomer);
      repository.save(procedure);
      return Optional.of(customerMsvc);
    }
    return Optional.empty();
  }

  //crear un nuevo Usuario para el tramite
  @Override
  @Transactional
  public Optional<Customer> createCustomer(Customer customer, Long procedureId) {
    Optional<Procedure> o = repository.findById(procedureId);
    if(o.isPresent()){
      Customer customerNewMsvc = client.create(customer);

      Procedure procedure = o.get();
      ProcedureCustomer procedureCustomer = new ProcedureCustomer();
      procedureCustomer.setId(customerNewMsvc.getId());

      procedure.addProcedureCustomer(procedureCustomer);
      repository.save(procedure);
      return Optional.of(customerNewMsvc);
    }
    return Optional.empty();
  }

  //desasignar usuario de tramite
  @Override
  @Transactional
  public Optional<Customer> removeCustomer(Customer customer, Long procedureId) {
    Optional<Procedure> o = repository.findById(procedureId);
    if(o.isPresent()){
      Customer customerMsvc = client.detail(customer.getId());

      Procedure procedure = o.get();
      ProcedureCustomer procedureCustomer = new ProcedureCustomer();
      procedureCustomer.setId(customerMsvc.getId());

      procedure.removeProcedureCustomer(procedureCustomer);  //---> ProcedureCustomer metodo equals
      repository.save(procedure);
      return Optional.of(customerMsvc);
    }
    return Optional.empty();
  }

}
