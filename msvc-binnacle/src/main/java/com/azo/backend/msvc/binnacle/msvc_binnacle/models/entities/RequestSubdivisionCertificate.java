package com.azo.backend.msvc.binnacle.msvc_binnacle.models.entities;

import com.azo.backend.msvc.binnacle.msvc_binnacle.enums.RequestStatus;

// import java.util.List;
// import java.util.ArrayList;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;

import jakarta.persistence.OneToOne;

@Entity
@DiscriminatorValue("CERTIFICADO_NO_PERTENENCIA")
public class RequestSubdivisionCertificate extends Request {

  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "subdivision_certificate_id")
  private SubdivisionCertificate subdivisionCertificate;

  @Override
  public void process() {
    this.setStatus(RequestStatus.EN_REVISION);
    // Generar el certificado de subdivisión
    SubdivisionCertificate certificate = new SubdivisionCertificate();
    certificate.setRequest(this);
    certificate.setDetails("Certificado de No Pertenencia en proceso");
    this.setSubdivisionCertificate(certificate);
  }

  public void generateCertificate() {
    if (this.subdivisionCertificate == null) {
        this.subdivisionCertificate = new SubdivisionCertificate();
        this.subdivisionCertificate.setRequest(this);
    }
    this.subdivisionCertificate.setDetails("Certificado de No Pertenencia generado");
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
  
}
