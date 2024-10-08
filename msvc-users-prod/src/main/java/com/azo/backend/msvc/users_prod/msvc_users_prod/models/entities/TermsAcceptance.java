package com.azo.backend.msvc.users_prod.msvc_users_prod.models.entities;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "terms_acceptances",
       uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "terms_id"}))
//crear fechas de creación y  mod automaticas
@EntityListeners(AuditingEntityListener.class)
public class TermsAcceptance {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne
  @JoinColumn(name = "terms_id", nullable = false)
  private TermsOfService termsOfService;

  @Column(nullable = false)
  private String ipAddress;

  @Column(nullable = false)
  private boolean accepted;

  @CreatedDate
  @Column(nullable = false, updatable = false)
  private LocalDateTime acceptanceDate;

  @LastModifiedDate
  @Column(nullable = false)
  private LocalDateTime lastModifiedDate;

  //Getters and Setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public TermsOfService getTermsOfService() {
    return termsOfService;
  }

  public void setTermsOfService(TermsOfService termsOfService) {
    this.termsOfService = termsOfService;
  }

  public String getIpAddress() {
    return ipAddress;
  }

  public void setIpAddress(String ipAddress) {
    this.ipAddress = ipAddress;
  }

  public boolean isAccepted() {
    return accepted;
  }

  public void setAccepted(boolean accepted) {
    this.accepted = accepted;
  }

}
