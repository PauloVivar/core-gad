package com.azo.backend.msvc.binnacle.msvc_binnacle.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
//import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.CadastralRecordDto;
import com.azo.backend.msvc.binnacle.msvc_binnacle.services.CadastralRecordService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/cadastral-records")
@CrossOrigin(originPatterns = "*")
@Tag(name = "CadastralRecordController", description = "Operaciones relacionadas con las fichas catastrales")
public class CadastralRecordController {

  @Autowired
  private CadastralRecordService service;

  @GetMapping("/page/{page}")
  public ResponseEntity<Page<CadastralRecordDto>> findAll(
    @PathVariable int page, 
    @RequestParam(defaultValue = "10") int size) {
      return ResponseEntity.ok(service.findAll(PageRequest.of(page, size)));
  }

  @GetMapping("/{cadastralCode}")
  public ResponseEntity<CadastralRecordDto> findById(@PathVariable String cadastralCode) {
      return service.findById(cadastralCode)
              .map(ResponseEntity::ok)
              .orElse(ResponseEntity.notFound().build());
  }
  
  @GetMapping("/citizen/{citizenId}")
  public ResponseEntity<?> findByCitizenId(@PathVariable Long citizenId) {
    List<CadastralRecordDto> records = service.findByCitizenId(citizenId);
    if (records.isEmpty()) {
        return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(records);
  }
  
  // @PostMapping
  // public ResponseEntity<CadastralRecordDto> create(@RequestBody CadastralRecordDto cadastralRecordDto) {
  //     return ResponseEntity.status(HttpStatus.CREATED).body(service.save(cadastralRecordDto));
  // }
}
