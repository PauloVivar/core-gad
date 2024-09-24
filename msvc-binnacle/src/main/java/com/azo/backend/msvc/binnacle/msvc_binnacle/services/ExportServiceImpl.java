package com.azo.backend.msvc.binnacle.msvc_binnacle.services;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.azo.backend.msvc.binnacle.msvc_binnacle.models.entities.Request;

import net.sf.jasperreports.engine.JRException;

public class ExportServiceImpl implements ExportService {

  @Autowired
  private JasperReportsService jasperReportsService;

  @Override
  public Resource exportRequests(List<Request> requests, String format) throws JRException, IOException {
    String reportName = "requests_report";
    Map<String, Object> parameters = new HashMap<>();
    parameters.put("createdBy", "Sistema de Gestión");

    byte[] reportContent;
    String contentType;
    String fileExtension;

    if ("pdf".equalsIgnoreCase(format)) {
        reportContent = jasperReportsService.generatePdfReport(reportName, parameters, requests);
        contentType = MediaType.APPLICATION_PDF_VALUE;
        fileExtension = "pdf";
    } else if ("excel".equalsIgnoreCase(format)) {
        reportContent = jasperReportsService.generateExcelReport(reportName, parameters, requests);
        contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        fileExtension = "xlsx";
    } else {
        throw new IllegalArgumentException("Formato no soportado: " + format);
    }

    ByteArrayResource resource = new ByteArrayResource(reportContent);

    return (Resource) ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(contentType))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=requests_report." + fileExtension)
            .body(resource);
  }
  
}
