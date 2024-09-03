package com.azo.backend.msvc.avaluos_catastros.msvc_avaluos_catastros.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

//paso 3: entidad que relaciones a tramirtes con msvc_users_prod

@Entity
@Table(name = "procedures_users")
public class ProcedureUser {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "user_id", unique = true)
  private Long userId;

  //getters and setters

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  //metodo para sobreescribir y usarlo en removeProcedureUser de la entidad Tramite(Procedure)
  @Override
  public boolean equals(Object obj) {
    if(this == obj){
      return true;
    }
    if(!(obj instanceof ProcedureUser)){
      return false;
    }

    ProcedureUser o = (ProcedureUser) obj;
    return this.userId != null && this.userId.equals(o.userId);
  }

}
