package com.azo.backend.msvc.binnacle.msvc_binnacle.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SpringSecurityConfig {

  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
      .authorizeHttpRequests(authRules -> authRules
        .requestMatchers(HttpMethod.GET, "/api/v1/requests", "/api/v1/requests/{id}", "/api/v1/requests/status/{status}", "/api/v1/requests/user/{userId}").permitAll()
        .requestMatchers(HttpMethod.GET, "/api/v1/cadastral-records", "/api/v1/cadastral-records/{cadastralCode}", "/api/v1/cadastral-records/citizen/{citizenId}").permitAll()
        .requestMatchers(HttpMethod.GET, "/api/v1/subdivision-certificates").permitAll()
        .requestMatchers(HttpMethod.GET, "/api/v1/dashboard").permitAll()

        .requestMatchers(HttpMethod.POST, "/api/v1/requests").permitAll()
        .requestMatchers(HttpMethod.POST, "/api/v1/subdivision-certificates").permitAll()
        .requestMatchers(HttpMethod.POST, "/api/v1/dashboard").permitAll()
        .requestMatchers(HttpMethod.DELETE, "/api/v1/requests/**").permitAll()
        .requestMatchers("/api/v1/requests/**").permitAll()
        .requestMatchers("/api/v1/cadastral-records/**").permitAll()
        .requestMatchers("/api/v1/subdivision-certificates/**").permitAll()
        .requestMatchers("/api/v1/dashboard/**").permitAll()
        .requestMatchers("/error").permitAll()
        .anyRequest().authenticated())

      .csrf(config -> config.disable())                                                                    //desabilitar cuanto es API-REST, Monolito viene por defecto
      .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .cors(cors -> cors.configurationSource(corsConfigurationSource()));
    return http.build();
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
      CorsConfiguration config = new CorsConfiguration();
      config.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
      config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
      config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
      config.setAllowCredentials(true);

      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
      source.registerCorsConfiguration("/**", config);
      return source;
  }
}
