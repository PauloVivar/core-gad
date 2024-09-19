package com.azo.backend.msvc.binnacle.msvc_binnacle.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

  @Bean
  public OpenAPI requestOpenAPI() {
    return new OpenAPI()
            .info(new Info()
                    .title("Documentación de la API Solicitudes")
                    .description("Solicitudes para trámites que realiza un ciudadano y gestión del mismo")
                    .version("1.0.0"));
  }
  
}
