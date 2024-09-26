package com.azo.backend.msvc.binnacle.msvc_binnacle.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfig {

  @Bean
  public FeignClientInterceptor feignClientInterceptor() {
      return new FeignClientInterceptor();
  }
  
}
