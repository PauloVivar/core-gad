package com.azo.backend.msvc.binnacle.msvc_binnacle.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.CorrectionDto;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.mapper.DtoMapperCorrection;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.entities.Correction;
import com.azo.backend.msvc.binnacle.msvc_binnacle.repositories.CorrectionRepository;

@Service
public class CorrectionServiceImpl implements CorrectionService {

  @Autowired
  private CorrectionRepository repository;

  @Override
  @Transactional(readOnly = true)
  public List<CorrectionDto> findAll() {
    List<Correction> corrections = (List<Correction>) repository.findAll();
    return corrections.stream()
            .map(c -> DtoMapperCorrection.builder().setCorrection(c).build())
            .collect(Collectors.toList());
    //return (List<Correction>) repository.findAll();
  }

  @Override
  @Transactional(readOnly = true)
	public Page<CorrectionDto> findAllByRequestId(Long requestId, Pageable pageable) {
		Page<Correction> corrections = repository.findAllByRequestId(requestId, pageable);
    return corrections
              .map(c -> DtoMapperCorrection.builder().setCorrection(c).build());
	}

  @Override
  @Transactional(readOnly = true)
  public Optional<CorrectionDto> findById(Long id) {
    return repository.findById(id)
            .map(c -> DtoMapperCorrection.builder().setCorrection(c).build());
  }

  @Override
  @Transactional
  public CorrectionDto save(CorrectionDto correctionDto) {
    Correction correction = new Correction();
    correction.setRequestId(correctionDto.getRequestId());
    correction.setDescription(correctionDto.getDescription());
    correction.setDateNotified(correctionDto.getDateNotified());
    correction.setDeadline(correctionDto.getDeadline());
    correction.setStatus(correctionDto.getStatus());
    
    Correction savedCorrection = repository.save(correction);
    return DtoMapperCorrection.builder().setCorrection(savedCorrection).build();
    //return repository.save(correction);
  }

  @Override
  @Transactional
  public Optional<CorrectionDto> update(CorrectionDto correctionDto, Long id) {
    Optional<Correction> o = repository.findById(id);
    if (o.isPresent()) {
        Correction correctionDb = o.get();
        correctionDb.setRequestId(correctionDto.getRequestId());
        correctionDb.setDescription(correctionDto.getDescription());
        correctionDb.setDateNotified(correctionDto.getDateNotified());
        correctionDb.setDeadline(correctionDto.getDeadline());
        correctionDb.setStatus(correctionDto.getStatus());

        Correction updatedCorrection = repository.save(correctionDb);
        return Optional.of(DtoMapperCorrection.builder().setCorrection(updatedCorrection).build());
    }
    return Optional.empty();
  }

  @Override
  @Transactional
  public void remove(Long id) {
    repository.deleteById(id);
  }

  @Override
  @Transactional(readOnly = true)
  public List<CorrectionDto> findByRequestId(Long requestId) {
    List<Correction> corrections = repository.findByRequestId(requestId);
        return corrections.stream()
                .map(c -> DtoMapperCorrection.builder().setCorrection(c).build())
                .collect(Collectors.toList());
    //return repository.findByRequestId(requestId);
  }

}
