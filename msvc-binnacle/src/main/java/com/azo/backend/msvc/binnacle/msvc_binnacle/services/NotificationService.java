package com.azo.backend.msvc.binnacle.msvc_binnacle.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.NotificationDto;

public interface NotificationService {

  List<NotificationDto> findAll();
  Page<NotificationDto> findAllByRequestId(Long requestId, Pageable pageable);
  Optional<NotificationDto> findById(UUID id);
  NotificationDto save(NotificationDto notification);
  Optional<NotificationDto> update(NotificationDto notification, UUID id);
  void remove(UUID id);
  List<NotificationDto> findByRequestId(Long requestId);
  
}
