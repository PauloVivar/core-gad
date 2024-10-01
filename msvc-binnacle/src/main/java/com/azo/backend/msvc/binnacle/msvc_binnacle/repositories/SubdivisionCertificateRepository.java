package com.azo.backend.msvc.binnacle.msvc_binnacle.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.azo.backend.msvc.binnacle.msvc_binnacle.models.entities.SubdivisionCertificate;

@Repository
public interface SubdivisionCertificateRepository extends JpaRepository<SubdivisionCertificate, Long> {

  // Método para buscar certificados de subdivisión por el ID de solicitud
  Optional<SubdivisionCertificate> findByRequestId(Long requestId);

  // Optional<SubdivisionCertificate> findByCadastralRecordIdAndId(Long cadastralRecordId, Long id);
  // List<SubdivisionCertificate> findByDetailsContaining(String keyword);
  
}
