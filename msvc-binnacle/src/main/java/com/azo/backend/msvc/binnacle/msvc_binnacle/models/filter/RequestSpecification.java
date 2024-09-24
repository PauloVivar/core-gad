package com.azo.backend.msvc.binnacle.msvc_binnacle.models.filter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.azo.backend.msvc.binnacle.msvc_binnacle.models.entities.Request;

import jakarta.persistence.criteria.Predicate;

public class RequestSpecification {

  public static Specification<Request> withFilter(RequestFilter filter) {
    return (root, query, criteriaBuilder) -> {
        List<Predicate> predicates = new ArrayList<>();

        if (filter.getStatus() != null) {
            predicates.add(criteriaBuilder.equal(root.get("status"), filter.getStatus()));
        }
        if (filter.getCadastralCode() != null && !filter.getCadastralCode().isEmpty()) {
            predicates.add(criteriaBuilder.equal(root.get("cadastralCode"), filter.getCadastralCode()));
        }
        if (filter.getEntryDate() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("entryDate"), filter.getEntryDate()));
        }
        if (filter.getEndDate() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("endDate"), filter.getEndDate()));
        }
        if (filter.getUserId() != null) {
            predicates.add(criteriaBuilder.or(
                criteriaBuilder.equal(root.get("citizenId"), filter.getUserId()),
                criteriaBuilder.equal(root.get("assignedToUserId"), filter.getUserId())
            ));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    };
  } 
}
