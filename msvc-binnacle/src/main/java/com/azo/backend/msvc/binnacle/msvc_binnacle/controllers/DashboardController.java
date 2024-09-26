package com.azo.backend.msvc.binnacle.msvc_binnacle.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.azo.backend.msvc.binnacle.msvc_binnacle.enums.RequestStatus;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.DashboardDto;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.NotificationDto;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.RequestDetailDto;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.dtos_dashboard.DashboardStatisticsDto;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.dtos_dashboard.DashboardSummaryDto;
import com.azo.backend.msvc.binnacle.msvc_binnacle.services.DashboardService;

import net.sf.jasperreports.engine.JRException;

@RestController
@RequestMapping("/api/v1/dashboard")
@CrossOrigin(originPatterns = "*")
public class DashboardController {

  @Autowired
  private DashboardService dashboardService;

  @GetMapping("/requests/status/{status}")
  public ResponseEntity<List<RequestDetailDto>> getRequestsByStatus(@PathVariable RequestStatus status) {
      return ResponseEntity.ok(dashboardService.getRequestsByStatus(status));
  }

  @GetMapping("/requests/user/{userId}")
  public ResponseEntity<List<RequestDetailDto>> getRequestsByUser(@PathVariable Long userId) {
      return ResponseEntity.ok(dashboardService.getRequestsByUser(userId));
  }

  @GetMapping("/requests/assignedUser/{userId}")
  public ResponseEntity<List<RequestDetailDto>> getRequestsByAssignedUser(@PathVariable Long userId) {
      List<RequestDetailDto> requests = dashboardService.getRequestsByAssignedUser(userId);
      return ResponseEntity.ok(requests);
  }

  @GetMapping("/requests/count/{status}")
  public ResponseEntity<Integer> countRequestsByStatus(@PathVariable RequestStatus status) {
      return ResponseEntity.ok(dashboardService.countRequestsByStatus(status));
  }

  @GetMapping("/report")
  public ResponseEntity<DashboardDto> generateStatisticalReport() {
      return ResponseEntity.ok(dashboardService.generateStatisticalReport());
  }

  //new endpoints
  //ojo arreglar
  @GetMapping("/summary")
  public ResponseEntity<DashboardSummaryDto> getSummary() {
      return ResponseEntity.ok(dashboardService.generateSummary());
  }

  @GetMapping("/statistics")
  public ResponseEntity<DashboardStatisticsDto> getStatistics() {
      return ResponseEntity.ok(dashboardService.generateStatistics());
  }

  //ojo arreglar
  @PutMapping("/requests/{id}/assign")
  public ResponseEntity<RequestDetailDto> assignRequest(@PathVariable Long id, @RequestBody Long userId) {
      return ResponseEntity.ok(dashboardService.assignRequest(id, userId));
  }

  //ojo arreglar
  @PutMapping("/requests/{id}/status")
  public ResponseEntity<RequestDetailDto> updateRequestStatus(@PathVariable Long id, @RequestBody RequestStatus status) {
      return ResponseEntity.ok(dashboardService.updateRequestStatus(id, status));
  }

  //ojo arreglar
  @PostMapping("/requests/{id}/notifications")
  public ResponseEntity<Void> sendNotification(@PathVariable Long id, @RequestBody NotificationDto notificationDto) {
      dashboardService.sendNotification(id, notificationDto);
      return ResponseEntity.ok().build();
  }

  @PutMapping("/requests/{id}/approve")
  public ResponseEntity<RequestDetailDto> approveRequest(@PathVariable Long id) {
      return ResponseEntity.ok(dashboardService.approveRequest(id));
  }

  @PutMapping("/requests/{id}/reject")
  public ResponseEntity<RequestDetailDto> rejectRequest(@PathVariable Long id) {
      return ResponseEntity.ok(dashboardService.rejectRequest(id));
  }

  //ojo arreglar
  @PutMapping("/requests/{id}/reassign")
  public ResponseEntity<RequestDetailDto> reassignRequest(@PathVariable Long id, @RequestBody Long newUserId) {
      return ResponseEntity.ok(dashboardService.reassignRequest(id, newUserId));
  }

  //ojo arreglar
  @GetMapping("/requests/export")
  public ResponseEntity<Resource> exportRequests(@RequestParam(defaultValue = "excel") String format) {
      try {
          Resource resource = dashboardService.exportRequests(format);
          return ResponseEntity.ok()
                  .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=requests_report." + format)
                  .body(resource);
      } catch (JRException | IOException e) {
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
      }
  }
  
}
