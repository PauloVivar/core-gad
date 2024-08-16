package com.azo.backend.msvc.users_prod.msvc_users_prod.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.azo.backend.msvc.users_prod.msvc_users_prod.models.entities.TermsOfService;

public interface TermsOfServiceRepository extends CrudRepository<TermsOfService, Long> {

  Optional<TermsOfService> findTopByOrderByEffectiveDateDesc();
  Optional<TermsOfService> findByVersion(String version);
                 
}
