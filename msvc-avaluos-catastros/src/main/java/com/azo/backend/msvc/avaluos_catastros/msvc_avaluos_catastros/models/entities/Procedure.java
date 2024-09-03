package com.azo.backend.msvc.avaluos_catastros.msvc_avaluos_catastros.models.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.azo.backend.msvc.avaluos_catastros.msvc_avaluos_catastros.models.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotEmpty;

//paso 1: entidad Trámites

@Entity
@Table(name = "procedures")
public class Procedure {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotEmpty
  private String status;

  @NotEmpty
  private String typeProcedure;

  @Column(name = "start_date", nullable = false)
  private LocalDateTime startDate;

  @Column(name = "end_date", nullable = false)
  private LocalDateTime endDate;

  @PrePersist
  protected void onCreate() {
    startDate = LocalDateTime.now();
  }

  //procedure_id la llave foranea que se creará en la tabla ProcedureCustomer
  //tipo de user que se va a persistir
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "procedure_id")
  private List<ProcedureUser> procedureUsers;

  //este  atributo no se mapea a la db, se utiliza para poblar los datos de users desde
  //el msvc-users-prod (comunicación entre microservicios)
  //tipo de user que no se va a persistir
  @Transient
  private List<User> users;

  //constructor para instanciar ProcedureUser
  public Procedure() {
    procedureUsers = new ArrayList<>();
    users = new ArrayList<>();
  }

  //getters and setters

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getTypeProcedure() {
    return typeProcedure;
  }

  public void setTypeProcedure(String typeProcedure) {
    this.typeProcedure = typeProcedure;
  }

  public LocalDateTime getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDateTime startDate) {
    this.startDate = startDate;
  }

  public LocalDateTime getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDateTime endDate) {
    this.endDate = endDate;
  }

  public List<ProcedureUser> getProcedureUsers() {
    return procedureUsers;
  }

  public void setProcedureUsers(List<ProcedureUser> procedureUsers) {
    this.procedureUsers = procedureUsers;
  }

  public List<User> getUsers() {
    return users;
  }

  public void setUsers(List<User> users) {
    this.users = users;
  }

  //metodos para agregar y quitar de la lista los usuarios de los trámites
  public void addProcedureUser(ProcedureUser procedureUs){
    procedureUsers.add(procedureUs);
  }

  //se debe comparar por el id y no por la instacia ya que los objetos son unicos, se agrega
  //metodos equals en ProcedureUser
  public void removeProcedureUser(ProcedureUser procedureUs){
    procedureUsers.remove(procedureUs);
  }

}
