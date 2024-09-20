package com.azo.backend.msvc.binnacle.msvc_binnacle.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.azo.backend.msvc.binnacle.msvc_binnacle.clients.UserClientRest;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.User;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.NotificationDto;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.mapper.DtoMapperNotification;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.entities.Notification;
import com.azo.backend.msvc.binnacle.msvc_binnacle.repositories.NotificationRepository;

import feign.FeignException;

@Service
public class NotificationServiceImpl implements NotificationService {

  private static final Logger log = LoggerFactory.getLogger(NotificationServiceImpl.class);

  @Autowired
  private NotificationRepository repository;

  @Autowired
  private UserClientRest userClientRest;

  @Override
  @Transactional(readOnly = true)
  public List<NotificationDto> findAll() {
    List<Notification> notifications = (List<Notification>) repository.findAll();
    return notifications
            .stream()
            .map(n -> enrichNotificationDto(
                DtoMapperNotification.builder().setNotification(n).build()))
            .collect(Collectors.toList());
  }

  @Override
  @Transactional(readOnly = true)
	public Page<NotificationDto> findAllByRequestId(Long requestId, Pageable pageable) {
		Page<Notification> notifications = repository.findAllByRequestId(requestId, pageable);
    return notifications
              .map(n -> enrichNotificationDto(
                  DtoMapperNotification.builder().setNotification(n).build()));
	}

  @Override
  @Transactional(readOnly = true)
  public Optional<NotificationDto> findById(UUID id) {
    return repository.findById(id)
            .map(n -> enrichNotificationDto(
                DtoMapperNotification.builder().setNotification(n).build()));
  }

  @Override
  @Transactional
  public NotificationDto save(NotificationDto notificationDto) {
    Notification notification = new Notification();
    notification.setDate(notificationDto.getDate());
    notification.setContent(notificationDto.getContent());
    notification.setAddresseeId(notificationDto.getAddresseeId());
    notification.setType(notificationDto.getType());
    notification.setRequestId(notificationDto.getRequestId());
    
    Notification savedNotification = repository.save(notification);
    return enrichNotificationDto(
              DtoMapperNotification.builder().setNotification(savedNotification).build());
  }

  @Override
  @Transactional
  public Optional<NotificationDto> update(NotificationDto notificationDto, UUID id) {
    return repository.findById(id)
              .map(notificationDb -> {
                  notificationDb.setDate(notificationDto.getDate());
                  notificationDb.setContent(notificationDto.getContent());
                  notificationDb.setAddresseeId(notificationDto.getAddresseeId());
                  notificationDb.setType(notificationDto.getType());
                  notificationDb.setRequestId(notificationDto.getRequestId());
                  return enrichNotificationDto(
                            DtoMapperNotification.builder().setNotification(repository.save(notificationDb)).build());
              });
  }

  @Override
  @Transactional
  public void remove(UUID id) {
    repository.deleteById(id);
  }

  @Override
  @Transactional(readOnly = true)
  public List<NotificationDto> findByRequestId(Long requestId) {
    List<Notification> notifications = repository.findByRequestId(requestId);
        return notifications.stream()
                .map(n -> enrichNotificationDto(
                    DtoMapperNotification.builder().setNotification(n).build()))
                .collect(Collectors.toList());
  }

  //Método utils
  private NotificationDto enrichNotificationDto(NotificationDto dto) {
    try {
        User addressee = userClientRest.detail(dto.getAddresseeId());
        dto.setAddresseeName(addressee.getUsername());
        dto.setAddresseeEmail(addressee.getEmail());
    } catch (FeignException.NotFound e) {
        log.warn("Destinatario no encontrado para el ID: {}. Error: {}", dto.getAddresseeId(), e.getMessage());
        dto.setAddresseeName("Destinatario no encontrado");
        dto.setAddresseeEmail("N/A");
    } catch (Exception e) {
        log.error("Error inesperado al obtener información del addressee: {}", e.getMessage());
        dto.setAddresseeName("Error al obtener información del addressee");
        dto.setAddresseeEmail("Error inesperado");
    }
    return dto;
  }

}
