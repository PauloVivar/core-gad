package com.azo.backend.msvc.binnacle.msvc_binnacle.config;

import org.springframework.beans.factory.annotation.Value;

import feign.RequestInterceptor;
import feign.RequestTemplate;

public class FeignClientInterceptor implements RequestInterceptor {

  @Value("${inter-service.secret}")
  private String interServiceSecret;

  @Override
  public void apply(RequestTemplate template) {
    template.header("Authorization", "Bearer " + interServiceSecret);

    template.header("Access-Control-Allow-Origin", "*");
    template.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
    template.header("Access-Control-Allow-Headers", "Authorization, Content-Type");
  }
  
}
