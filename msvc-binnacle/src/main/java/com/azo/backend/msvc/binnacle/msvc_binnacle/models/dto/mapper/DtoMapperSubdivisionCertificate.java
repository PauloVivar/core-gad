package com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.mapper;

import com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.SubdivisionCertificateDto;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.entities.Request;
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
          subdivisionCertificate.getRequest() != null ? subdivisionCertificate.getRequest().getId() : null
      );
  }

  public static SubdivisionCertificateDto build(SubdivisionCertificate subdivisionCertificate) {
    return builder().setSubdivisionCertificate(subdivisionCertificate).build();
  }

  public static SubdivisionCertificate toEntity(SubdivisionCertificateDto dto) {
      SubdivisionCertificate certificate = new SubdivisionCertificate();
      certificate.setId(dto.getId());
      certificate.setDetails(dto.getDetails());
      // No establecemos la relación con Request aquí, eso se hará en el servicio
      return certificate;
  }

  public static SubdivisionCertificate toEntity(SubdivisionCertificateDto dto, Request request) {
      SubdivisionCertificate certificate = toEntity(dto);
      certificate.setRequest(request);
      return certificate;
  }
  
}
