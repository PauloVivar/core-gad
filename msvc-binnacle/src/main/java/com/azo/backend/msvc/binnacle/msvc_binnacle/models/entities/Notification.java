package com.azo.backend.msvc.binnacle.msvc_binnacle.models.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import com.azo.backend.msvc.binnacle.msvc_binnacle.common.GeneratedUUID;
import com.azo.backend.msvc.binnacle.msvc_binnacle.enums.NotificationType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;

//paso 1: entidad Notificaciones

@Entity
@Table(name = "notifications")
public class Notification {

  @Id
  @GeneratedUUID
  private UUID id;

  @NotEmpty
  @Column(nullable = false)
  private LocalDateTime date;

  @PrePersist
  protected void onCreate() {
    date = LocalDateTime.now();
  }

  @Column(nullable = false)
  private String content;

  @Column(name = "addressee_id", nullable = false)
  private Long addresseeId;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private NotificationType type;

  @Column(name = "request_id", nullable = false)
  private Long requestId;

  //Getters and setters
  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public LocalDateTime getDate() {
    return date;
  }

  public void setDate(LocalDateTime date) {
    this.date = date;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Long getAddresseeId() {
    return addresseeId;
  }

  public void setAddresseeId(Long addresseeId) {
    this.addresseeId = addresseeId;
  }

  public NotificationType getType() {
    return type;
  }

  public void setType(NotificationType type) {
    this.type = type;
  }

  public Long getRequestId() {
    return requestId;
  }

  public void setRequestId(Long requestId) {
    this.requestId = requestId;
  }

}
 