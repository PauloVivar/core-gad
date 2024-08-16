package com.azo.backend.msvc.users_prod.msvc_users_prod.models.entities;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.azo.backend.msvc.users_prod.msvc_users_prod.models.IUser;
import com.azo.backend.msvc.users_prod.msvc_users_prod.models.ConstantsConfig;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

//1. Primero Crear Entidad -> Entity User

@Entity
@Table(name="users")
//crear fechas de creación y mod automaticas
@EntityListeners(AuditingEntityListener.class)
public class User implements IUser {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "El username es requerido.")
  @Column(nullable = false, unique = true)
  private String username;

  @NotBlank(message = "El password es requerido.")
  @Column(nullable = false)
  private String password;

  @NotEmpty(message = "El email es requerido.")
  @Email(message = "Ingrese un email válido.")
  @Column(nullable = false, unique = true)
  private String email;

  //Relación muchos a muchos, utilización de tabla ternaria
  @ManyToMany
  @JoinTable(
    name = "users_roles",
    joinColumns = @JoinColumn(name="user_id"),
    inverseJoinColumns = @JoinColumn(name="role_id"),
    uniqueConstraints = { @UniqueConstraint(columnNames = {"user_id", "role_id"}) }
  )
  private List<Role> roles;

  //Transient para no mapear este campo en la DB
  @Transient
  private boolean admin;

  //nuevos campos
  private String cellphone;
	private String phone;
	private String avatar;

  @Pattern(regexp = "ACTIVO|INACTIVO", message = "El estado debe ser ACTIVO o INACTIVO")
  @Column(columnDefinition = "VARCHAR(10) DEFAULT 'ACTIVO'")
  private String status = ConstantsConfig.STATUS_ACTIVE;

  @CreatedDate
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @LastModifiedDate
  @Column(name = "updated_at", nullable = false)
  private LocalDateTime updatedAt;

  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private Contribuyente contribuyente;

  //Getters and Setters
  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  public String getUsername() {
    return username;
  }
  public void setUsername(String username) {
    this.username = username;
  }
  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
  }
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  public List<Role> getRoles() {
    return roles;
  }
  public void setRoles(List<Role> roles) {
    this.roles = roles;
  }

  //Como se implementa IUser (Interfaz User) indicamos que isAdmin estamos sobre escribiendo
  @Override
  public boolean isAdmin() {
    return admin;
  }
  public void setAdmin(boolean admin) {
    this.admin = admin;
  }
  public String getCellphone() {
    return cellphone;
  }
  public void setCellphone(String cellphone) {
    this.cellphone = cellphone;
  }
  public String getPhone() {
    return phone;
  }
  public void setPhone(String phone) {
    this.phone = phone;
  }
  public String getAvatar() {
    return avatar;
  }
  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }
  public String getStatus() {
    return status;
  }
  public void setStatus(String status) {
    this.status = status;
  }

  public Contribuyente getContribuyente() {
    return contribuyente;
  }
  
  public void setContribuyente(Contribuyente contribuyente) {
    this.contribuyente = contribuyente;
  }

}
