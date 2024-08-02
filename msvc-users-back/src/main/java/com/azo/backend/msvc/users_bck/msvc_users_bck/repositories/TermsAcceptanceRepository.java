package com.azo.backend.msvc.users_bck.msvc_users_bck.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.azo.backend.msvc.users_bck.msvc_users_bck.models.entities.TermsAcceptance;
import com.azo.backend.msvc.users_bck.msvc_users_bck.models.entities.TermsOfService;
import com.azo.backend.msvc.users_bck.msvc_users_bck.models.entities.User;

public interface TermsAcceptanceRepository extends CrudRepository<TermsAcceptance, Long> {

  Optional<TermsAcceptance> findTopByUserAndTermsOfServiceOrderByAcceptanceDateDesc(
    User user, 
    TermsOfService termsOfService);
  
  Optional<TermsAcceptance> findByUserAndTermsOfService(User user, TermsOfService termsOfService);
  
  //test
  void deleteByUserId(Long userId);
}
