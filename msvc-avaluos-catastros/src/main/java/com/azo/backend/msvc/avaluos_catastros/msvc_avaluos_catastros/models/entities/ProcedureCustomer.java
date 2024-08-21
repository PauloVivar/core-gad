package com.azo.backend.msvc.avaluos_catastros.msvc_avaluos_catastros.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

//paso 1

@Entity
@Table(name = "procedures_customers")
public class ProcedureCustomer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "customer_id", unique = true)
  private Long customerId;

  //getters and setters

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getCustomerId() {
    return customerId;
  }

  public void setCustomerId(Long customerId) {
    this.customerId = customerId;
  }

  //metodo para sobreescribir y usarlo en removeProcedureCustomer de entidad Tramite(Procedure)
  @Override
  public boolean equals(Object obj) {
    if(this == obj){
      return true;
    }
    if((obj instanceof ProcedureCustomer)){
      return false;
    }

    ProcedureCustomer o = (ProcedureCustomer) obj;
    return this.customerId != null && this.customerId.equals(o.customerId);
  }

}