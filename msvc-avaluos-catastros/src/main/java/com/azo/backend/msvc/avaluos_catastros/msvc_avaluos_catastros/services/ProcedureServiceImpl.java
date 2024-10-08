package com.azo.backend.msvc.avaluos_catastros.msvc_avaluos_catastros.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.azo.backend.msvc.avaluos_catastros.msvc_avaluos_catastros.clients.UserClientRest;
import com.azo.backend.msvc.avaluos_catastros.msvc_avaluos_catastros.models.User;
import com.azo.backend.msvc.avaluos_catastros.msvc_avaluos_catastros.models.entities.Procedure;
import com.azo.backend.msvc.avaluos_catastros.msvc_avaluos_catastros.models.entities.ProcedureUser;
import com.azo.backend.msvc.avaluos_catastros.msvc_avaluos_catastros.repositories.ProcedureRepository;

//paso 4.1.
@Service
public class ProcedureServiceImpl implements ProcedureService {

  @Autowired
  private ProcedureRepository repository;

  @Autowired
  private UserClientRest client;

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
  @Transactional(readOnly = true)
  public Optional<Procedure> findByIdWithUsers(Long id) {
    Optional<Procedure> o = repository.findById(id);
    if(o.isPresent()){
      Procedure procedure = o.orElseThrow();
      if(!procedure.getProcedureUsers().isEmpty()){
        //List<Long> ids = procedure.getProcedureUsers().stream().map(cu -> cu.getUserId()).toList();
        List<Long> ids = procedure.getProcedureUsers().stream().map(ProcedureUser::getUserId).toList();

        List<User> users = client.getUsersByProcedure(ids);
        procedure.setUsers(users);
      }
      return Optional.of(procedure);
    }
    return Optional.empty();
  }

  @Override
  @Transactional
  public Procedure save(Procedure procedure) {
    return repository.save(procedure);
  }

  @Override
  @Transactional
  public void remove(Long id) {
    repository.deleteById(id);
  }

  @Override
  @Transactional
  public void removeProcedureUserById(Long id) {
    repository.removeProcedureUserById(id);
  }

  //asignar usuario a tramite
  @Override
  @Transactional
  public Optional<User> assignUser(User user, Long procedureId) {
    Optional<Procedure> o = repository.findById(procedureId);
    if(o.isPresent()){
      User userMsvc = client.detail(user.getId());
      //System.out.println("Pruebaaaaa : " + userMsvc);
      Procedure procedure = o.orElseThrow();
      ProcedureUser procedureUser = new ProcedureUser();
      procedureUser.setId(userMsvc.getId());

      procedure.addProcedureUser(procedureUser);
      repository.save(procedure);
      return Optional.of(userMsvc);
    }
    return Optional.empty();
  }

  //crear un nuevo Usuario para el tramite
  @Override
  @Transactional
  public Optional<User> createUser(User user, Long procedureId) {
    Optional<Procedure> o = repository.findById(procedureId);
    if(o.isPresent()){
      User userNewMsvc = client.create(user);
      Procedure procedure = o.orElseThrow();
      ProcedureUser procedureUser = new ProcedureUser();
      procedureUser.setId(userNewMsvc.getId());

      procedure.addProcedureUser(procedureUser);
      repository.save(procedure);
      return Optional.of(userNewMsvc);
    }
    return Optional.empty();
  }

  //desasignar usuario de tramite
  @Override
  @Transactional
  public Optional<User> removeUser(User user, Long procedureId) {
    Optional<Procedure> o = repository.findById(procedureId);
    if(o.isPresent()){
      User userMsvc = client.detail(user.getId());

      Procedure procedure = o.orElseThrow();
      ProcedureUser procedureUser = new ProcedureUser();
      procedureUser.setId(userMsvc.getId());

      procedure.removeProcedureUser(procedureUser);  //---> ProcedureUser metodo equals
      repository.save(procedure);
      return Optional.of(userMsvc);
    }
    return Optional.empty();
  }

}
