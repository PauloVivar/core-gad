package com.azo.backend.msvc.users_prod.msvc_users_prod.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.azo.backend.msvc.users_prod.msvc_users_prod.models.entities.TermsAcceptance;
import com.azo.backend.msvc.users_prod.msvc_users_prod.models.entities.TermsOfService;
import com.azo.backend.msvc.users_prod.msvc_users_prod.models.entities.User;

public interface TermsAcceptanceRepository extends CrudRepository<TermsAcceptance, Long> {

  Optional<TermsAcceptance> findTopByUserAndTermsOfServiceOrderByAcceptanceDateDesc(
    User user, 
    TermsOfService termsOfService);
  
  Optional<TermsAcceptance> findByUserAndTermsOfService(User user, TermsOfService termsOfService);
  
  //test
  void deleteByUserId(Long userId);
}
