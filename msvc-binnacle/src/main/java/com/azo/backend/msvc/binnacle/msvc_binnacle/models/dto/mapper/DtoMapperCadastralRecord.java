package com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.mapper;

import com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.CadastralRecordDto;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.entities.CadastralRecord;

public class DtoMapperCadastralRecord {
  private CadastralRecord cadastralRecord;

    private DtoMapperCadastralRecord() {
    }

    public static DtoMapperCadastralRecord builder() {
        return new DtoMapperCadastralRecord();
    }

    public DtoMapperCadastralRecord setCadastralRecord(CadastralRecord cadastralRecord) {
        this.cadastralRecord = cadastralRecord;
        return this;
    }

    public CadastralRecordDto build() {
        if (cadastralRecord == null) {
            throw new RuntimeException("Debe pasar el Entity CadastralRecord!");
        }
        return new CadastralRecordDto(
            cadastralRecord.getCadastralCode(),
            cadastralRecord.getCity(),
            cadastralRecord.getProvince(),
            cadastralRecord.getCountry(),
            cadastralRecord.getDeedDate(),
            cadastralRecord.getRegistrationDate(),
            cadastralRecord.getCompanyDate(),
            cadastralRecord.getUpdateDate(),
            cadastralRecord.getHeritageZone(),
            cadastralRecord.getStatus()
        );
    }

    public static CadastralRecord toEntity(CadastralRecordDto dto) {
        CadastralRecord record = new CadastralRecord();
        record.setCadastralCode(dto.getCadastralCode());
        record.setCity(dto.getCity());
        record.setProvince(dto.getProvince());
        record.setCountry(dto.getCountry());
        record.setDeedDate(dto.getDeedDate());
        record.setRegistrationDate(dto.getRegistrationDate());
        record.setCompanyDate(dto.getCompanyDate());
        record.setUpdateDate(dto.getUpdateDate());
        record.setHeritageZone(dto.getHeritageZone());
        record.setStatus(dto.getStatus());
        return record;
    }
  
}
