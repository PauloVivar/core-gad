package com.azo.backend.msvc.binnacle.msvc_binnacle.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.TechnicalReviewDto;

public interface TechnicalReviewService {

  List<TechnicalReviewDto> findAll();
  //listar todos las revisiones con paginación por solicitud
  Page<TechnicalReviewDto> findAllByRequestId(Long requestId, Pageable pageable);
  Optional<TechnicalReviewDto> findById(Long id);
  TechnicalReviewDto save(TechnicalReviewDto review);
  Optional<TechnicalReviewDto> update(TechnicalReviewDto review, Long id);
  void remove(Long id);
  List<TechnicalReviewDto> findByRequestId(Long requestId);

}
