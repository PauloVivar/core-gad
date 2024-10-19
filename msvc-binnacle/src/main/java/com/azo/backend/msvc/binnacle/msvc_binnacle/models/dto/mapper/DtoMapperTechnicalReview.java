package com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.mapper;

import com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.TechnicalReviewDto;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.entities.TechnicalReview;

public class DtoMapperTechnicalReview {
  
  private TechnicalReview technicalReview;

  private DtoMapperTechnicalReview() {
  }

  public static DtoMapperTechnicalReview builder() {
    return new DtoMapperTechnicalReview();
  }

  public DtoMapperTechnicalReview setTechnicalReview(TechnicalReview technicalReview) {
    this.technicalReview = technicalReview;
    return this;
  }

  public TechnicalReviewDto build() {
    if(technicalReview == null) {
        throw new RuntimeException("Debe pasar el Entity TechnicalReview!");
    }
    return new TechnicalReviewDto(
        technicalReview.getId(),
        //technicalReview.getRequestId(),
        technicalReview.getRequest() != null ? technicalReview.getRequest().getId() : null,
        technicalReview.getReviewerId(),
        technicalReview.getDate(),
        technicalReview.getComments(),
        technicalReview.getResult()
    );
  }
}
