package com.azo.backend.msvc.binnacle.msvc_binnacle.models.entities;

import com.azo.backend.msvc.binnacle.msvc_binnacle.enums.RequestStatus;

// import java.util.List;
// import java.util.ArrayList;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;

import jakarta.persistence.OneToOne;

import java.util.List;
import java.util.ArrayList;

@Entity
@DiscriminatorValue("CERTIFICADO_NO_PERTENENCIA")
public class RequestSubdivisionCertificate extends Request {

  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "subdivision_certificate_id")
  private SubdivisionCertificate subdivisionCertificate;

  @Override
  public void process() {
    this.setStatus(RequestStatus.INGRESADO);
    // Generar el certificado de subdivisión
    SubdivisionCertificate certificate = new SubdivisionCertificate();
    certificate.setRequest(this);
    certificate.setDetails("Generación de certificado en proceso");
    this.setSubdivisionCertificate(certificate);
  }

  public void generateCertificate() {
    if (this.subdivisionCertificate == null) {
        this.subdivisionCertificate = new SubdivisionCertificate();
        this.subdivisionCertificate.setRequest(this);
    }
    this.subdivisionCertificate.setDetails("Certificado generado");
    this.setStatus(RequestStatus.APROBADO);
  }

  //Getters and Setters
  
  public SubdivisionCertificate getSubdivisionCertificate() {
    return subdivisionCertificate;
  }

  public void setSubdivisionCertificate(SubdivisionCertificate subdivisionCertificate) {
    this.subdivisionCertificate = subdivisionCertificate;
    if (subdivisionCertificate != null) {
        subdivisionCertificate.setRequest(this);
    }
  }

  @Override
  public List<TechnicalReview> getTechnicalReviews() {
      return new ArrayList<>(); // O null si no aplica
  }

  @Override
  public List<Correction> getCorrections() {
      return new ArrayList<>(); // O null si no aplica
  }

}
