package com.azo.backend.msvc.binnacle.msvc_binnacle.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.CorrectionDto;

public interface CorrectionService {
  List<CorrectionDto> findAll();
  Page<CorrectionDto> findAll(Pageable pageable);
  Optional<CorrectionDto> findById(Long id);
  CorrectionDto save(CorrectionDto correction);
  Optional<CorrectionDto> update(CorrectionDto correction, Long id);
  void remove(Long id);
  List<CorrectionDto> findByRequestId(Long requestId);
}
