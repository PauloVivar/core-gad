package com.azo.backend.msvc.binnacle.msvc_binnacle.services;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.azo.backend.msvc.binnacle.msvc_binnacle.clients.UserClientRest;
import com.azo.backend.msvc.binnacle.msvc_binnacle.enums.RequestStatus;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.User;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.DashboardDto;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.RequestDetailDto;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.entities.Request;
import com.azo.backend.msvc.binnacle.msvc_binnacle.repositories.RequestRepository;

@Service
public class DashboardService {

  private static final Logger log = LoggerFactory.getLogger(NotificationServiceImpl.class);
  @Autowired
  private UserClientRest userClientRest;

  @Autowired
  private RequestRepository requestRepository;

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
      long pendingRequests = requestCountByStatus.getOrDefault(RequestStatus.PENDIENTE_SUBSANACION, 0L)
                            + requestCountByStatus.getOrDefault(RequestStatus.EN_REVISION, 0L);

      return new DashboardDto(requestCountByStatus, totalRequests, pendingRequests);
  }

  private RequestDetailDto convertToDto(Request request) {
    RequestDetailDto dto = new RequestDetailDto();
    
    // Mapear propiedades de Request a RequestDto
    dto.setId(request.getId());
    dto.setStatus(request.getStatus());
    dto.setEntryDate(request.getEntryDate());
    dto.setCitizenId(request.getCitizenId());
    dto.setCadastralCode(request.getCadastralCode());
    dto.setAssignedToUserId(request.getAssignedToUserId());
    
    // Obtener información del usuario desde el microservicio users-prod
    try {
        User user = userClientRest.detail(request.getCitizenId());
        // Asumiendo que RequestDto tiene un campo para almacenar información adicional del usuario
        dto.setCitizenName(user.getUsername());
        dto.setCitizenEmail(user.getEmail());
    } catch (Exception e) {
        // Manejar el error si no se puede obtener la información del usuario
        log.error("Error al obtener información del usuario: " + e.getMessage());
    }
    
    return dto;
  }
  
}
