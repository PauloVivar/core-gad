package com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.mapper;

import com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.CorrectionDto;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.entities.Correction;

public class DtoMapperCorrection {
  private Correction correction;

  private DtoMapperCorrection() {
  }

  public static DtoMapperCorrection builder() {
    return new DtoMapperCorrection();
  }

  public DtoMapperCorrection setCorrection(Correction correction) {
    this.correction = correction;
    return this;
  }

  public CorrectionDto build() {
    if(correction == null) {
        throw new RuntimeException("Debe pasar el Entity Correction!");
    }
    return new CorrectionDto(
        correction.getId(),
        //correction.getRequestId(),
        correction.getRequest() != null ? correction.getRequest().getId() : null,
        correction.getDescription(),
        correction.getDateNotified(),
        correction.getDeadline(),
        correction.getStatus()
    );
  }
  
}
