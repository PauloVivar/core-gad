package com.azo.backend.msvc.binnacle.msvc_binnacle.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.CadastralRecordDto;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.mapper.DtoMapperCadastralRecord;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.entities.CadastralRecord;
import com.azo.backend.msvc.binnacle.msvc_binnacle.repositories.CadastralRecordRepository;

public class CadastralRecordServiceImpl implements CadastralRecordService{

  @Autowired
  private CadastralRecordRepository repository;

  @Override
  public List<CadastralRecordDto> findAll() {
    List<CadastralRecord> cadastralRecords = (List<CadastralRecord>) repository.findAll();
    return cadastralRecords.stream()
            .map(c -> DtoMapperCadastralRecord.builder().setCadastralRecord(c).build())
            .collect(Collectors.toList());
  }

  @Override
  public Page<CadastralRecordDto> findAll(Pageable pageable) {
    Page<CadastralRecord> cadastralRecords = repository.findAll(pageable);
    return cadastralRecords
              .map(c -> DtoMapperCadastralRecord.builder().setCadastralRecord(c).build());
  }

  @Override
  public Optional<CadastralRecordDto> findById(String code) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findById'");
  }

  @Override
  public CadastralRecordDto save(CadastralRecordDto cadastralRecordDto) {
    CadastralRecord record = DtoMapperCadastralRecord.toEntity(cadastralRecordDto);
    record = repository.save(record);
    return DtoMapperCadastralRecord.builder().setCadastralRecord(record).build();
  }

  @Override
  public Optional<CadastralRecordDto> update(CadastralRecordDto cadastralRecordDto, String code) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'update'");
  }

  @Override
  public void delete(String code) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'delete'");
  }

  @Override
  public Optional<CadastralRecordDto> findByCitizenId(Long citizenId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findByCitizenId'");
  }



  
  
}
