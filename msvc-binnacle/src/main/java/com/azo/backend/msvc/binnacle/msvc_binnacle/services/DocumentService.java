package com.azo.backend.msvc.binnacle.msvc_binnacle.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.DocumentDto;

public interface DocumentService  {
  List<DocumentDto> findAll();
  List<DocumentDto> findAllByRequestId(Long requestId);
  Page<DocumentDto> findAllPageByRequestId(Long requestId, Pageable pageable);
  Optional<DocumentDto> findById(Long id);
  DocumentDto save(DocumentDto documentDto);
  Optional<DocumentDto> update(DocumentDto documentDto, Long id);
  void remove(Long id);
  List<DocumentDto> saveAll(List<DocumentDto> documents, Long requestId);
  void removeAllByRequestId(Long requestId);
}
