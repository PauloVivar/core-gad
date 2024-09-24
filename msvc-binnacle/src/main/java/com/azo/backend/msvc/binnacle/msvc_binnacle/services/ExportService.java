package com.azo.backend.msvc.binnacle.msvc_binnacle.services;

import java.io.IOException;
import java.util.List;

import org.springframework.core.io.Resource;

import com.azo.backend.msvc.binnacle.msvc_binnacle.models.entities.Request;

import net.sf.jasperreports.engine.JRException;

public interface ExportService {
  
  Resource exportRequests(List<Request> requests, String format) throws JRException, IOException;
  
}