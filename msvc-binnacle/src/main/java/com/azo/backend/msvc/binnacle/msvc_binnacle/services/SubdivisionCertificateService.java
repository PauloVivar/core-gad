package com.azo.backend.msvc.binnacle.msvc_binnacle.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.azo.backend.msvc.binnacle.msvc_binnacle.enums.SubdivisionType;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.SubdivisionCertificateDto;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.validations.ValidationResult;

import jakarta.persistence.EntityNotFoundException;

public interface SubdivisionCertificateService {

  List<SubdivisionCertificateDto> findAll();
  Page<SubdivisionCertificateDto> findAll(Pageable pageable);
  Optional<SubdivisionCertificateDto> findById(Long id);
  SubdivisionCertificateDto save(SubdivisionCertificateDto subdivisionCertificateDto);
  Optional<SubdivisionCertificateDto> update(SubdivisionCertificateDto subdivisionCertificateDto, Long id);
  void delete(Long id);
  Optional<SubdivisionCertificateDto> findByRequestId(Long requestId);

  ValidationResult validateDocumentsAndStatus(Long requestId);

  byte[] generateCertificate(Long requestId) throws Exception;

  SubdivisionCertificateDto updateSubdivisionType(Long id, SubdivisionType type) throws EntityNotFoundException;
  
}
