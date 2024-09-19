package com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.mapper;

import com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.NotificationDto;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.entities.Notification;

public class DtoMapperNotification {
  
  private Notification notification;

  private DtoMapperNotification() {
  }

  public static DtoMapperNotification builder() {
    return new DtoMapperNotification();
  }

  public DtoMapperNotification setNotification(Notification notification) {
    this.notification = notification;
    return this;
  }

  public NotificationDto build() {
    if(notification == null) {
        throw new RuntimeException("Debe pasar el Entity Notification!");
    }
    return new NotificationDto(
        notification.getId(),
        notification.getDate(),
        notification.getContent(),
        notification.getAddresseeId(),
        notification.getType(),
        notification.getRequestId()
    );
  }
}
