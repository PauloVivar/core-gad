package com.azo.backend.msvc.binnacle.msvc_binnacle.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.azo.backend.msvc.binnacle.msvc_binnacle.models.entities.CadastralRecord;

@Repository
public interface CadastralRecordRepository extends JpaRepository<CadastralRecord, String> {

  // List<CadastralRecord> findByCity(String city);
  // List<CadastralRecord> findByStatusAndHeritageZone(Integer status, Integer heritageZone);

}
