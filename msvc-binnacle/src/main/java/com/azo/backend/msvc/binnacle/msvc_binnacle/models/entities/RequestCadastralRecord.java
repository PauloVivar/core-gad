package com.azo.backend.msvc.binnacle.msvc_binnacle.models.entities;

import java.util.List;
import java.util.ArrayList;

import java.time.LocalDateTime;

import com.azo.backend.msvc.binnacle.msvc_binnacle.enums.RequestStatus;
import com.azo.backend.msvc.binnacle.msvc_binnacle.enums.ReviewResult;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

@Entity
@DiscriminatorValue("FICHA_CATASTRAL")
public class RequestCadastralRecord extends Request {

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "request_id")
  private List<TechnicalReview> technicalReviews = new ArrayList<>();

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "request_id")
  private List<Correction> corrections = new ArrayList<>();

  @Override
  public void process() {
    // Implementación específica para procesar una solicitud de ficha catastral
    this.setStatus(RequestStatus.INGRESADO);
    // Crear una revisión técnica inicial
    initializeInitialReview();
  }

  private void initializeInitialReview() {
    TechnicalReview initialReview = new TechnicalReview();
    initialReview.setRequestId(this.getId());
    initialReview.setDate(LocalDateTime.now());
    initialReview.setComments("Revisión inicial");
    initialReview.setResult(ReviewResult.EN_PROCESO);
    this.addTechnicalReview(initialReview);
  }

  // Métodos específicos para manejar revisiones técnicas
  public void addTechnicalReview(TechnicalReview review) {
      technicalReviews.add(review);
      review.setRequestId(this.getId());
  }

  public void removeTechnicalReview(TechnicalReview review) {
      technicalReviews.remove(review);
  }

  public void addCorrection(Correction correction) {
      corrections.add(correction);
      correction.setRequestId(this.getId());
  }

  public void removeCorrection(Correction correction) {
      corrections.remove(correction);
  }

  // Getters y setters

  @Override
  public List<TechnicalReview> getTechnicalReviews() {
      return technicalReviews;
  }

  @Override
  public List<Correction> getCorrections() {
      return corrections;
  }

  public void setTechnicalReviews(List<TechnicalReview> technicalReviews) {
      this.technicalReviews = technicalReviews;
  }

  public void setCorrections(List<Correction> corrections) {
      this.corrections = corrections;
  }
  
}
