package com.azo.backend.msvc.binnacle.msvc_binnacle.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.CadastralRecordDto;

public interface CadastralRecordService {
  List<CadastralRecordDto> findAll();
  Page<CadastralRecordDto> findAll(Pageable pageable);
  Optional<CadastralRecordDto> findById(String code);
  //CadastralRecordDto save(CadastralRecordDto cadastralRecordDto);
  Optional<CadastralRecordDto> findByCitizenId(Long citizenId);
  
}
