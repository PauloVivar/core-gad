package com.azo.backend.msvc.binnacle.msvc_binnacle.models.entities;

import java.time.LocalDateTime;

import com.azo.backend.msvc.binnacle.msvc_binnacle.enums.ReviewResult;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "technical_reviews")
public class TechnicalReview {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // @Column(name = "request_id", nullable = false)
  // private Long requestId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "request_id", nullable = false)
  private Request request;

  @Column(name = "reviewer_id", nullable = true)
  private Long reviewerId;

  @NotEmpty
  @Column(nullable = false)
  private LocalDateTime date;

  @PrePersist
  protected void onCreate() {
    date = LocalDateTime.now();
  }

  @Column(length = 1000)
  private String comments;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private ReviewResult result;

  @PreRemove
  private void preRemove() {
      this.request = null;
  }

  // Getters and setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  // public Long getRequestId() {
  //   return requestId;
  // }

  // public void setRequestId(Long requestId) {
  //   this.requestId = requestId;
  // }

  public Request getRequest() {
    return request;
  }

  public void setRequest(Request request) {
    this.request = request;
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

}
