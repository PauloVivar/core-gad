package com.azo.backend.msvc.binnacle.msvc_binnacle.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.SubdivisionCertificateDto;

public interface SubdivisionCertificateService {

  List<SubdivisionCertificateDto> findAll();
  Page<SubdivisionCertificateDto> findAll(Pageable pageable);
  Optional<SubdivisionCertificateDto> findById(Long id);
  SubdivisionCertificateDto save(SubdivisionCertificateDto subdivisionCertificateDto);
  Optional<SubdivisionCertificateDto> update(SubdivisionCertificateDto subdivisionCertificateDto, Long id);
  void delete(Long id);
  Optional<SubdivisionCertificateDto> findByRequestId(Long requestId);
  
}
