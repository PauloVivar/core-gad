package com.azo.backend.msvc.binnacle.msvc_binnacle.enums;

public enum SubdivisionType {

  SI_PERTENECE("SI PERTENECE A PROPIEDAD HORIZONTAL APROBADO"),
  NO_PERTENECE("NO PERTENECE A PROPIEDAD HORIZONTAL APROBADO");

  private final String description;

  SubdivisionType(String description) {
      this.description = description;
  }

  public String getDescription() {
      return description;
  }
  
}
