package com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.azo.backend.msvc.binnacle.msvc_binnacle.enums.NotificationType;

public class NotificationDto {
  private UUID id;
  private LocalDateTime date;
  private String content;
  private Long addresseeId;
  private String addresseeName;  // Obtenido del msvc-users
  private String addresseeEmail; // Obtenido del msvc-users
  private NotificationType type;
  private Long requestId;

  public NotificationDto() {
  }

  public NotificationDto(
    UUID id, 
    LocalDateTime date, 
    String content, 
    Long addresseeId, 
    NotificationType type, 
    Long requestId) {
      this.id = id;
      this.date = date;
      this.content = content;
      this.addresseeId = addresseeId;
      this.type = type;
      this.requestId = requestId;
  }

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

  public String getAddresseeName() {
    return addresseeName;
  }

  public void setAddresseeName(String addresseeName) {
    this.addresseeName = addresseeName;
  }

  public String getAddresseeEmail() {
    return addresseeEmail;
  }

  public void setAddresseeEmail(String addresseeEmail) {
    this.addresseeEmail = addresseeEmail;
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
