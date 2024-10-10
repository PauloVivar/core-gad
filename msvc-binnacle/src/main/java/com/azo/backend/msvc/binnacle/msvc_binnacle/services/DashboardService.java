package com.azo.backend.msvc.binnacle.msvc_binnacle.services;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.azo.backend.msvc.binnacle.msvc_binnacle.clients.UserClientRest;
import com.azo.backend.msvc.binnacle.msvc_binnacle.enums.RequestStatus;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.User;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.DashboardDto;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.NotificationDto;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.RequestDetailDto;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.dtos_dashboard.DashboardStatisticsDto;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.dtos_dashboard.DashboardSummaryDto;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.entities.Request;
import com.azo.backend.msvc.binnacle.msvc_binnacle.repositories.RequestRepository;

import jakarta.persistence.EntityNotFoundException;
import net.sf.jasperreports.engine.JRException;

@Service
public class DashboardService {

  private static final Logger log = LoggerFactory.getLogger(NotificationServiceImpl.class);
  
  @Autowired
  private UserClientRest userClientRest;

  @Autowired
  private RequestRepository requestRepository;

  @Autowired
  private NotificationService notificationService;

  @Autowired
  private ExportService exportService;

  public List<RequestDetailDto> getRequestsByStatus(RequestStatus status) {
      List<Request> requests = requestRepository.findByStatus(status);
      return requests.stream()
              .map(this::convertToDto)
              .collect(Collectors.toList());
  }

  public List<RequestDetailDto> getRequestsByUser(Long userId) {
      List<Request> requests = requestRepository.findByCitizenId(userId);
      return requests.stream()
              .map(this::convertToDto)
              .collect(Collectors.toList());
  }

  public List<RequestDetailDto> getRequestsByAssignedUser(Long userId) {
      List<Request> requests = requestRepository.findByAssignedToUserId(userId);
      return requests.stream()
              .map(this::convertToDto)
              .collect(Collectors.toList());
  }

  public int countRequestsByStatus(RequestStatus status) {
      return (int) requestRepository.countByStatus(status);
  }

  public DashboardDto generateStatisticalReport() {
      Map<RequestStatus, Long> requestCountByStatus = Arrays.stream(RequestStatus.values())
              .collect(Collectors.toMap(
                  status -> status,
                  status -> (long) requestRepository.countByStatus(status)
              ));

      long totalRequests = requestCountByStatus.values().stream().mapToLong(Long::longValue).sum();
      long pendingRequests = requestCountByStatus.getOrDefault(RequestStatus.PENDIENTE, 0L)
                            + requestCountByStatus.getOrDefault(RequestStatus.EN_REVISION, 0L);

      return new DashboardDto(requestCountByStatus, totalRequests, pendingRequests);
  }

  // Método: Generar resumen
  public DashboardSummaryDto generateSummary() {
    DashboardSummaryDto summary = new DashboardSummaryDto();
    summary.setTotalRequests(requestRepository.count());
    summary.setRequestCountByStatus(requestRepository.countByStatus());
    summary.setAverageResolutionTime(requestRepository.calculateAverageResolutionTime());
    summary.setPendingRequests(requestRepository.countByStatus().get(RequestStatus.PENDIENTE));
    return summary;
  }

  // Nuevo método: Generar estadísticas
  public DashboardStatisticsDto generateStatistics() {
    DashboardStatisticsDto statistics = new DashboardStatisticsDto();
    statistics.setRequestsByStatusOverTime(requestRepository.getRequestsByStatusOverTime());
    statistics.setRequestsByType(requestRepository.countByType());
    statistics.setRequestsByUser(requestRepository.countByUser());
    return statistics;
  }

  // Método: Asignar solicitud
  @Transactional
  public RequestDetailDto assignRequest(Long id, Long userId) {
    Request request = requestRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Solicitud no encontrada"));
    request.setAssignedToUserId(userId);
    return convertToDto(requestRepository.save(request));
  }

  // Método: Actualizar estado de solicitud
  @Transactional
  public RequestDetailDto updateRequestStatus(Long id, RequestStatus status) {
    Request request = requestRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Solicitud no encontrada"));
    request.setStatus(status);
    return convertToDto(requestRepository.save(request));
  }

  // Metodo: Enviar notificación
  public void sendNotification(Long id, NotificationDto notificationDto) {
    RequestDetailDto requestDetail = findRequestDetailById(id);
        notificationService.sendNotification(requestDetail, notificationDto);
  }

  private RequestDetailDto findRequestDetailById(Long id) {
    Request request = requestRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Solicitud no encontrada"));
    return convertToDto(request);
  }

  // Metodos: Acciones rápidas
  @Transactional
  public RequestDetailDto approveRequest(Long id) {
      return updateRequestStatus(id, RequestStatus.APROBADO);
  }

  @Transactional
  public RequestDetailDto rejectRequest(Long id) {
      return updateRequestStatus(id, RequestStatus.RECHAZADO);
  }

  @Transactional
  public RequestDetailDto reassignRequest(Long id, Long newUserId) {
      return assignRequest(id, newUserId);
  }

  // Método: Exportar solicitudes
  public Resource exportRequests(String format) throws JRException, IOException {
    List<Request> requests = requestRepository.findAll();
    return exportService.exportRequests(requests, format);
  }

  private RequestDetailDto convertToDto(Request request) {
    RequestDetailDto dto = new RequestDetailDto();
    
    // Mapear propiedades de Request a RequestDto
    dto.setId(request.getId());
    dto.setStatus(request.getStatus());
    dto.setType(request.getType());
    dto.setEntryDate(request.getEntryDate());
    dto.setCitizenId(request.getCitizenId());
    dto.setCadastralCode(request.getCadastralRecord().getCadastralCode());
    dto.setAssignedToUserId(request.getAssignedToUserId());
    
    // Obtener información del usuario desde el microservicio users-prod para ciudadano
    try {
        User user = userClientRest.detail(request.getCitizenId());
        // Asumiendo que RequestDto tiene un campo para almacenar información adicional del usuario
        dto.setCitizenName(user.getUsername());
        dto.setCitizenEmail(user.getEmail());
    } catch (Exception e) {
        // Manejar el error si no se puede obtener la información del usuario
        log.error("Error al obtener información del usuario: " + e.getMessage());
    }

    // Obtener información del usuario desde el microservicio users-prod para revisor
    if (request.getAssignedToUserId() != null) {
      try {
          User assignedUser = userClientRest.detail(request.getAssignedToUserId());
          dto.setAssignedToUserName(assignedUser.getUsername());
          dto.setAssignedToUserEmail(assignedUser.getEmail());
      } catch (Exception e) {
          log.error("Error al obtener información del usuario asignado: " + e.getMessage());
      }
    }
    return dto;
  }
}
