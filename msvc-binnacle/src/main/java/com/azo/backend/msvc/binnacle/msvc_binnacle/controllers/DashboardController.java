package com.azo.backend.msvc.binnacle.msvc_binnacle.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.azo.backend.msvc.binnacle.msvc_binnacle.enums.RequestStatus;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.DashboardDto;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.RequestDetailDto;
import com.azo.backend.msvc.binnacle.msvc_binnacle.services.DashboardService;

@RestController
@RequestMapping("/api/dashboard")
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

  @GetMapping("/requests/count/{status}")
  public ResponseEntity<Integer> countRequestsByStatus(@PathVariable RequestStatus status) {
      return ResponseEntity.ok(dashboardService.countRequestsByStatus(status));
  }

  @GetMapping("/report")
  public ResponseEntity<DashboardDto> generateStatisticalReport() {
      return ResponseEntity.ok(dashboardService.generateStatisticalReport());
  }
  
}
