package com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto;

import java.time.LocalDateTime;

import com.azo.backend.msvc.binnacle.msvc_binnacle.enums.ReviewResult;

public class TechnicalReviewDto {
  private Long id;
  private Long requestId;
  private Long reviewerId;
  private String reviewerName;            // Obtenido del msvc-users
  private String reviewerEmail;           // Obtenido del msvc-users
  private LocalDateTime date;
  private String comments;
  private ReviewResult result;

  public TechnicalReviewDto() {

  }

  public TechnicalReviewDto(Long id, Long requestId, Long reviewerId, LocalDateTime date, String comments, ReviewResult result) {
    this.id = id;
    this.requestId = requestId;
    this.reviewerId = reviewerId;
    this.date = date;
    this.comments = comments;
    this.result = result;
  }

  //Getters and Setters

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getRequestId() {
    return requestId;
  }

  public void setRequestId(Long requestId) {
    this.requestId = requestId;
  }

  public Long getReviewerId() {
    return reviewerId;
  }

  public void setReviewerId(Long reviewerId) {
    this.reviewerId = reviewerId;
  }

  public LocalDateTime getDate() {
    return date;
  }

  public void setDate(LocalDateTime date) {
    this.date = date;
  }

  public String getComments() {
    return comments;
  }

  public void setComments(String comments) {
    this.comments = comments;
  }

  public ReviewResult getResult() {
    return result;
  }

  public void setResult(ReviewResult result) {
    this.result = result;
  }

  public String getReviewerName() {
    return reviewerName;
  }

  public void setReviewerName(String reviewerName) {
    this.reviewerName = reviewerName;
  }

  public String getReviewerEmail() {
    return reviewerEmail;
  }

  public void setReviewerEmail(String reviewerEmail) {
    this.reviewerEmail = reviewerEmail;
  }
  
}
