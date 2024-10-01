package com.azo.backend.msvc.binnacle.msvc_binnacle.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.SubdivisionCertificateDto;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.mapper.DtoMapperSubdivisionCertificate;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.entities.Request;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.entities.SubdivisionCertificate;
import com.azo.backend.msvc.binnacle.msvc_binnacle.repositories.RequestRepository;
import com.azo.backend.msvc.binnacle.msvc_binnacle.repositories.SubdivisionCertificateRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class SubdivisionCertificateServiceImpl implements SubdivisionCertificateService {

  @Autowired
  private SubdivisionCertificateRepository repository;

  @Autowired
  private RequestRepository requestRepository;

  @Override
  @Transactional(readOnly = true)
  public List<SubdivisionCertificateDto> findAll() {
    List<SubdivisionCertificate> subdivision = (List<SubdivisionCertificate>) repository.findAll();
    return subdivision.stream()
            .map(s -> DtoMapperSubdivisionCertificate.builder().setSubdivisionCertificate(s).build())
            .collect(Collectors.toList());
  }

  @Override
  @Transactional(readOnly = true)
  public Page<SubdivisionCertificateDto> findAll(Pageable pageable) {
    Page<SubdivisionCertificate> cadastralRecords = repository.findAll(pageable);
    return cadastralRecords
              .map(s -> DtoMapperSubdivisionCertificate.builder().setSubdivisionCertificate(s).build());
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<SubdivisionCertificateDto> findById(Long id) {
    return repository.findById(id)
            .map(s -> DtoMapperSubdivisionCertificate
                        .builder()
                        .setSubdivisionCertificate(s)
                        .build());
  }

  @Override
  @Transactional
  public SubdivisionCertificateDto save(SubdivisionCertificateDto subdivisionCertificateDto) {
    Request request = requestRepository.findById(subdivisionCertificateDto.getRequestId())
                .orElseThrow(() -> new EntityNotFoundException("Request not found"));
    
    SubdivisionCertificate certificate = DtoMapperSubdivisionCertificate.toEntity(subdivisionCertificateDto);
    certificate.setRequest(request);
    certificate = repository.save(certificate);
    return DtoMapperSubdivisionCertificate.build(certificate);
  }

  @Override
  @Transactional
  public Optional<SubdivisionCertificateDto> update(SubdivisionCertificateDto subdivisionCertificateDto, Long id) {
    return repository.findById(id)
                .map(certificate -> {
                    SubdivisionCertificate updatedCertificate = DtoMapperSubdivisionCertificate.toEntity(subdivisionCertificateDto);
                    updatedCertificate.setId(id);
                    updatedCertificate.setRequest(certificate.getRequest());
                    return DtoMapperSubdivisionCertificate.build(repository.save(updatedCertificate));
                });
  }

  @Override
  @Transactional
  public void delete(Long id) {
    repository.deleteById(id);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<SubdivisionCertificateDto> findByRequestId(Long requestId) {
    return repository.findByRequestId(requestId)
                .map(s -> DtoMapperSubdivisionCertificate
                            .builder()
                            .setSubdivisionCertificate(s)
                            .build());
  }
  
}
