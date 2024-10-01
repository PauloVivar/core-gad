package com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.mapper;

import com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.SubdivisionCertificateDto;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.entities.CadastralRecord;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.entities.SubdivisionCertificate;

public class DtoMapperSubdivisionCertificate {

  private SubdivisionCertificate subdivisionCertificate;

  private DtoMapperSubdivisionCertificate() {
  }

  public static DtoMapperSubdivisionCertificate builder() {
      return new DtoMapperSubdivisionCertificate();
  }

  public DtoMapperSubdivisionCertificate setSubdivisionCertificate(SubdivisionCertificate subdivisionCertificate) {
      this.subdivisionCertificate = subdivisionCertificate;
      return this;
  }

  public SubdivisionCertificateDto build() {
      if (subdivisionCertificate == null) {
          throw new RuntimeException("Debe pasar el Entity SubdivisionCertificate!");
      }
      return new SubdivisionCertificateDto(
          subdivisionCertificate.getId(),
          subdivisionCertificate.getDetails(),
          subdivisionCertificate.getCadastralRecord() != null ? subdivisionCertificate.getCadastralRecord().getCadastralCode() : null
      );
  }

  public static SubdivisionCertificate toEntity(SubdivisionCertificateDto dto, CadastralRecord cadastralRecord) {
      SubdivisionCertificate certificate = new SubdivisionCertificate();
      certificate.setId(dto.getId());
      certificate.setDetails(dto.getDetails());
      certificate.setCadastralRecord(cadastralRecord);
      return certificate;
  }
  
}
