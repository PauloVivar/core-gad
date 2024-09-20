package com.azo.backend.msvc.binnacle.msvc_binnacle.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.azo.backend.msvc.binnacle.msvc_binnacle.clients.UserClientRest;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.User;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.TechnicalReviewDto;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.mapper.DtoMapperTechnicalReview;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.entities.TechnicalReview;
import com.azo.backend.msvc.binnacle.msvc_binnacle.repositories.TechnicalReviewRepository;

import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class TechnicalReviewServiceImpl implements TechnicalReviewService {

  private static final Logger log = LoggerFactory.getLogger(TechnicalReviewServiceImpl.class);

  @Autowired
  private TechnicalReviewRepository repository;

  @Autowired
  private UserClientRest userClientRest;

  @Override
  @Transactional(readOnly = true)
  public List<TechnicalReviewDto> findAll() {
    List<TechnicalReview> reviews = (List<TechnicalReview>) repository.findAll();
    return reviews.stream()
            .map(r -> enrichTechnicalReviewDto(
              DtoMapperTechnicalReview.builder().setTechnicalReview(r).build()))
            .collect(Collectors.toList());
    //return (List<TechnicalReviewDto>) repository.findAll();
  }

  @Override
  @Transactional(readOnly = true)
	public Page<TechnicalReviewDto> findAllByRequestId(Long requestId, Pageable pageable) {
		Page<TechnicalReview> reviews = repository.findAllByRequestId(requestId, pageable);
    return reviews
            .map(r -> enrichTechnicalReviewDto(
                        DtoMapperTechnicalReview.builder().setTechnicalReview(r).build()));
	}

  @Override
  @Transactional(readOnly = true)
  public Optional<TechnicalReviewDto> findById(Long id) {
    return repository.findById(id)
            .map(r -> enrichTechnicalReviewDto(DtoMapperTechnicalReview
            .builder()
            .setTechnicalReview(r)
            .build()));
    //return repository.findById(id);
  }

  @Override
  @Transactional
  public TechnicalReviewDto save(TechnicalReviewDto reviewDto) {
    TechnicalReview review = new TechnicalReview();
    review.setRequestId(reviewDto.getRequestId());
    review.setReviewerId(reviewDto.getReviewerId());
    review.setDate(reviewDto.getDate());
    review.setComments(reviewDto.getComments());
    review.setResult(reviewDto.getResult());
    
    TechnicalReview savedReview = repository.save(review);
    return DtoMapperTechnicalReview.builder().setTechnicalReview(savedReview).build();
    //return repository.save(review);
  }

  @Override
  @Transactional
  public Optional<TechnicalReviewDto> update(TechnicalReviewDto reviewDto, Long id) {
    Optional<TechnicalReview> o = repository.findById(id);
    if (o.isPresent()) {
      TechnicalReview reviewDb = o.get();
      reviewDb.setRequestId(reviewDto.getRequestId());
      reviewDb.setReviewerId(reviewDto.getReviewerId());
      reviewDb.setDate(reviewDto.getDate());
      reviewDb.setComments(reviewDto.getComments());
      reviewDb.setResult(reviewDto.getResult());

      TechnicalReview updatedReview = repository.save(reviewDb);
      return Optional.of(DtoMapperTechnicalReview.builder().setTechnicalReview(updatedReview).build());
    }
    return Optional.empty();
  }

  @Override
  @Transactional
  public void remove(Long id) {
    repository.deleteById(id);
  }

  @Override
  @Transactional(readOnly = true)
  public List<TechnicalReviewDto> findByRequestId(Long requestId) {
      List<TechnicalReview> reviews = repository.findByRequestId(requestId);
      return reviews.stream()
              .map(r -> DtoMapperTechnicalReview.builder().setTechnicalReview(r).build())
              .collect(Collectors.toList());
  }

  //método aux
  private TechnicalReviewDto enrichTechnicalReviewDto(TechnicalReviewDto dto) {
    try {
      User reviewer = userClientRest.detail(dto.getReviewerId());
      dto.setReviewerName(reviewer.getUsername());
      dto.setReviewerEmail(reviewer.getEmail());
    } catch (FeignException.NotFound e) {
        log.warn("Revisor no encontrado para el ID: {}. Error: {}", dto.getReviewerId(), e.getMessage());
        dto.setReviewerName("Revisor no encontrado");
        dto.setReviewerEmail("N/A");
    } catch (Exception e) {
        log.error("Error inesperado al obtener información del revisor: {}", e.getMessage());
        dto.setReviewerName("Error al obtener información del revisor");
        dto.setReviewerEmail("Error inesperado");
    }
    return dto;
  }
}
