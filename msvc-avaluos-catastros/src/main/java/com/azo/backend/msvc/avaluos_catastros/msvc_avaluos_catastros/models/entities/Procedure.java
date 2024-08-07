package com.azo.backend.msvc.avaluos_catastros.msvc_avaluos_catastros.models.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.azo.backend.msvc.avaluos_catastros.msvc_avaluos_catastros.models.Customer;

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
  private List<ProcedureCustomer> procedureCustomers;

  //este  atributo no se mapea a la db, se utliza para poblar los datos de customers desde
  //el msvc-users-back (comunicadcion entre microservicios)
  //tipo de user que no se va a persistir
  @Transient
  private List<Customer> customers;

  //constructor para instanciar ProcedureCustomer
  public Procedure() {
    procedureCustomers = new ArrayList<>();
    customers = new ArrayList<>();
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

  public List<ProcedureCustomer> getProcedureCustomers() {
    return procedureCustomers;
  }

  public void setProcedureCustomers(List<ProcedureCustomer> procedureCustomers) {
    this.procedureCustomers = procedureCustomers;
  }

  public List<Customer> getCustomers() {
    return customers;
  }

  public void setCustomers(List<Customer> customers) {
    this.customers = customers;
  }

  //metodos para agregar y quitar de la lista los usuarios de los trámites
  public void addProcedureCustomer(ProcedureCustomer procedureCust){
    procedureCustomers.add(procedureCust);
  }

  //se debe comparar por el id y no por la instacia ya que los objetos son unicos, se agrega
  //metodod equals en ProcedureCustomer
  public void removeProcedureCustomer(ProcedureCustomer procedureCust){
    procedureCustomers.remove(procedureCust);
  }



}
