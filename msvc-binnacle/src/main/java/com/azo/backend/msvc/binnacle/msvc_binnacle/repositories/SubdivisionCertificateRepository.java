package com.azo.backend.msvc.binnacle.msvc_binnacle.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.azo.backend.msvc.binnacle.msvc_binnacle.models.entities.SubdivisionCertificate;

@Repository
public interface SubdivisionCertificateRepository extends JpaRepository<SubdivisionCertificate, Long> {

  // Método para buscar certificados de subdivisión por el ID del registro catastral
  List<SubdivisionCertificate> findByCadastralRecordId(Long cadastralRecordId);

  // Optional<SubdivisionCertificate> findByCadastralRecordIdAndId(Long cadastralRecordId, Long id);
  // List<SubdivisionCertificate> findByDetailsContaining(String keyword);
  
}
