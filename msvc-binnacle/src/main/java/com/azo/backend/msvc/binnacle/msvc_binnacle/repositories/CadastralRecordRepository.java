package com.azo.backend.msvc.binnacle.msvc_binnacle.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.azo.backend.msvc.binnacle.msvc_binnacle.models.entities.CadastralRecord;


@Repository
public interface CadastralRecordRepository extends JpaRepository<CadastralRecord, String> {

  // List<CadastralRecord> findByCity(String city);
  // List<CadastralRecord> findByStatusAndHeritageZone(Integer status, Integer heritageZone);
  @Query("SELECT cr FROM CadastralRecord cr WHERE cr.documentId = :documentId")
  Optional<CadastralRecord> findByDocumentId(@Param("documentId") String documentId);

}
