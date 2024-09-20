package com.azo.backend.msvc.binnacle.msvc_binnacle.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.azo.backend.msvc.binnacle.msvc_binnacle.models.entities.TechnicalReview;

public interface TechnicalReviewRepository extends CrudRepository<TechnicalReview, Long> {
  
  Page<TechnicalReview> findAllByRequestId(Long requestId, Pageable pageable);
  List<TechnicalReview> findByRequestId(Long requestId);

}
