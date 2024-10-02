package com.azo.backend.msvc.binnacle.msvc_binnacle.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.azo.backend.msvc.binnacle.msvc_binnacle.clients.UserClientRest;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.User;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.CadastralRecordDto;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.mapper.DtoMapperCadastralRecord;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.entities.CadastralRecord;
import com.azo.backend.msvc.binnacle.msvc_binnacle.repositories.CadastralRecordRepository;

@Service
public class CadastralRecordServiceImpl implements CadastralRecordService{

  @Autowired
  private CadastralRecordRepository repository;

  @Autowired
  private UserClientRest userClientRest;

  @Override
  @Transactional(readOnly = true)
  public Page<CadastralRecordDto> findAll(Pageable pageable) {
    Page<CadastralRecord> cadastralRecords = repository.findAll(pageable);
    return cadastralRecords
              .map(c -> DtoMapperCadastralRecord.builder().setCadastralRecord(c).build());
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<CadastralRecordDto> findById(String code) {
    return repository.findById(code)
            .map(c -> DtoMapperCadastralRecord
                          .builder()
                          .setCadastralRecord(c)
                          .build());
  }

  // @Override
  //@Transactional
  // public CadastralRecordDto save(CadastralRecordDto cadastralRecordDto) {
  //   CadastralRecord record = DtoMapperCadastralRecord.toEntity(cadastralRecordDto);
  //   record = repository.save(record);
  //   return DtoMapperCadastralRecord.builder().setCadastralRecord(record).build();
  // }

  @Override
  @Transactional(readOnly = true)
  public List<CadastralRecordDto> findByCitizenId(Long citizenId) {
    try {
      // Se obtiene el usuario del microservicio de usuarios
      User user = userClientRest.detail(citizenId);
      
      // Luego, se busca el CadastralRecord usando el documentId del usuario
      if (user != null && user.getContribuyenteCi() != null) {
        // Usa el contribuyenteCi para buscar los registros catastrales
        List<CadastralRecord> records = repository.findAllByDocumentId(user.getContribuyenteCi());
        return records.stream()
            .map(record -> DtoMapperCadastralRecord.builder().setCadastralRecord(record).build())
            .collect(Collectors.toList());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    // Si el usuario no se encuentra, retornamos un Optional vacío
    return Collections.emptyList();
  }
  
}
