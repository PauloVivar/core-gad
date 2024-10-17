package com.azo.backend.msvc.users_prod.msvc_users_prod.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

// @PropertySource("classpath:.env")
@Configuration
@PropertySources({
  @PropertySource(value = "classpath:.env", ignoreResourceNotFound = true),
  @PropertySource(value = "file:.env", ignoreResourceNotFound = true),
  @PropertySource(value = "file:/app/.env", ignoreResourceNotFound = true)
})
public class EnvConfig {
}
