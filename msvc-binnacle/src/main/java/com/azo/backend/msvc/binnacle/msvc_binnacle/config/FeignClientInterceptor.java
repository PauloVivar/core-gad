package com.azo.backend.msvc.binnacle.msvc_binnacle.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.RequestInterceptor;
import feign.RequestTemplate;

public class FeignClientInterceptor implements RequestInterceptor {

  // @Override
  // public void apply(RequestTemplate template) {
  //   template.header("Access-Control-Allow-Origin", "*");
  //   template.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
  //   template.header("Access-Control-Allow-Headers", "Authorization, Content-Type");
  // }

  private static final Logger logger = LoggerFactory.getLogger(FeignClientInterceptor.class);

  private static final String AUTHORIZATION_HEADER = "Authorization";

  @Override
  public void apply(RequestTemplate template) {
      ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
      if (requestAttributes != null) {
          String token = requestAttributes.getRequest().getHeader(AUTHORIZATION_HEADER);
          if (token != null) {
              logger.debug("Propagating token: {}", token);
              template.header(AUTHORIZATION_HEADER, token);
          } else {
              logger.warn("No token found in request. Proceeding without authentication.");
          }
      } else {
          logger.warn("RequestContextHolder is null. Unable to retrieve token.");
      }
  }
  
}
