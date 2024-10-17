package com.azo.backend.msvc.users_prod.msvc_users_prod.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import jakarta.annotation.PostConstruct;

@Configuration
public class MailConfigValidator {

  private static final Logger logger = LoggerFactory.getLogger(MailConfigValidator.class);

  // @Value("${spring.mail.password}")
  // private String mailPassword;
  // @PostConstruct
  // public void validateMailConfiguration() {
  //     if (!StringUtils.hasText(mailPassword)) {
  //         logger.warn("La contraseña de Spring Mail no está configurada. " +
  //                 "Asegúrate de configurar SPRING_MAIL_PASSWORD en el archivo .env");
  //     } else {
  //         logger.info("Configuración de Spring Mail cargada correctamente.");
  //     }
  // }

  @Value("${gmail.refresh.token}")
  private String refreshToken;

  @PostConstruct
  public void validateMailConfiguration() {
      if (!StringUtils.hasText(refreshToken)) {
          logger.warn("El refresh token de Gmail no está configurado. " +
                  "Asegúrate de configurar GMAIL_REFRESH_TOKEN en el archivo .env");
      } else {
          logger.info("Configuración de Gmail OAuth2 cargada correctamente.");
      }
  }

}
