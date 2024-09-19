package com.azo.backend.msvc.binnacle.msvc_binnacle.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.azo.backend.msvc.binnacle.msvc_binnacle.models.entities.Notification;

public interface NotificationRepository extends CrudRepository<Notification, UUID> {
  
  Page<Notification> findAll(Pageable pageable);
  List<Notification> findByRequestId(Long requestId);

}
