package com.azo.backend.msvc.binnacle.msvc_binnacle.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.SubdivisionCertificateDto;
import com.azo.backend.msvc.binnacle.msvc_binnacle.services.SubdivisionCertificateService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/subdivision-certificates")
@CrossOrigin(originPatterns = "*")
@Tag(name = "CadastralRecordController", description = "Operaciones relacionadas con los certificadfos de pertenercer o no a fraccionamiento")
public class SubdivisionCertificateController {

  @Autowired
  private SubdivisionCertificateService service;

  @GetMapping
  public ResponseEntity<List<SubdivisionCertificateDto>> findAll() {
      return ResponseEntity.ok(service.findAll());
  }

  @GetMapping("/page/{page}")
  public ResponseEntity<Page<SubdivisionCertificateDto>> findAll(
    @PathVariable int page, 
    @RequestParam(defaultValue = "10") int size) {
      return ResponseEntity.ok(service.findAll(PageRequest.of(page, size)));
  }

  @GetMapping("/{id}")
  public ResponseEntity<SubdivisionCertificateDto> findById(@PathVariable Long id) {
      return service.findById(id)
              .map(ResponseEntity::ok)
              .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<SubdivisionCertificateDto> create(@RequestBody SubdivisionCertificateDto subdivisionCertificateDto) {
      return ResponseEntity.status(HttpStatus.CREATED).body(service.save(subdivisionCertificateDto));
  }

  @PutMapping("/{id}")
  public ResponseEntity<SubdivisionCertificateDto> update(@RequestBody SubdivisionCertificateDto subdivisionCertificateDto, @PathVariable Long id) {
      return service.update(subdivisionCertificateDto, id)
              .map(ResponseEntity::ok)
              .orElse(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
      service.delete(id);
      return ResponseEntity.noContent().build();
  }

  @GetMapping("/request/{requestId}")
  public ResponseEntity<SubdivisionCertificateDto> findByRequestId(@PathVariable Long requestId) {
      return service.findByRequestId(requestId)
              .map(ResponseEntity::ok)
              .orElse(ResponseEntity.notFound().build());
  }
  
}
