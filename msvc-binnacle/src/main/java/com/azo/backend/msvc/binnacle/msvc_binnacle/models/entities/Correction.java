package com.azo.backend.msvc.binnacle.msvc_binnacle.models.entities;

import java.time.LocalDateTime;

import com.azo.backend.msvc.binnacle.msvc_binnacle.enums.CorrectionStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "corrections")
public class Correction {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // @Column(name = "request_id", nullable = false)
  // private Long requestId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "request_id", nullable = false)
  private Request request;

  @Column(length = 1000, nullable = false)
  private String description;

  @NotEmpty
  @Column(name = "date_notified", nullable = false)
  private LocalDateTime dateNotified;

  @PreUpdate
  protected void onUpdate() {
      this.dateNotified = LocalDateTime.now();
  }

  @Column(nullable = false)
  private LocalDateTime deadline;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private CorrectionStatus status;

  // Getters and setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  // public Long getRequestId() {
  //   return requestId;
  // }

  // public void setRequestId(Long requestId) {
  //   this.requestId = requestId;
  // }

  public Request getRequest() {
    return request;
  }

  public void setRequest(Request request) {
    this.request = request;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public LocalDateTime getDateNotified() {
    return dateNotified;
  }

  public void setDateNotified(LocalDateTime dateNotified) {
    this.dateNotified = dateNotified;
  }

  public LocalDateTime getDeadline() {
    return deadline;
  }

  public void setDeadline(LocalDateTime deadline) {
    this.deadline = deadline;
  }

  public CorrectionStatus getStatus() {
    return status;
  }

  public void setStatus(CorrectionStatus status) {
    this.status = status;
  }

}
