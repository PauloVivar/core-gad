package com.azo.backend.msvc.binnacle.msvc_binnacle.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.azo.backend.msvc.binnacle.msvc_binnacle.models.entities.Correction;

public interface CorrectionRepository extends CrudRepository<Correction, Long> {

  Page<Correction> findAll(Pageable pageable);
  List<Correction> findByRequestId(Long requestId);

}
