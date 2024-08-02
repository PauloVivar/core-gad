package com.azo.backend.msvc.users_bck.msvc_users_bck.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.azo.backend.msvc.users_bck.msvc_users_bck.models.entities.TermsOfService;

public interface TermsOfServiceRepository extends CrudRepository<TermsOfService, Long> {

  Optional<TermsOfService> findTopByOrderByEffectiveDateDesc();
  Optional<TermsOfService> findByVersion(String version);
                 
}
