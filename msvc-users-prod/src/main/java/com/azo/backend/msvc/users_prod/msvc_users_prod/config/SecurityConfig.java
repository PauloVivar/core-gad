package com.azo.backend.msvc.users_prod.msvc_users_prod.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

// 1. Primero -> Configuración de Spring Security para Login

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

  @Bean
  public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
  }

  // @Bean
  // SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
  //     return http
  //         .authorizeHttpRequests(auth -> auth
  //             .requestMatchers("/api/v1/users/registration").permitAll()
  //             .requestMatchers("/api/v1/users/search").permitAll()
  //             .anyRequest().authenticated())
  //         .oauth2ResourceServer(oauth2 -> oauth2
  //             .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())))
  //         .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
  //         .csrf(csrf -> csrf.disable())
  //         .build();
  // }

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
      http
          .authorizeHttpRequests(auth -> auth
              .requestMatchers("/authorized", "/login").permitAll()
              .requestMatchers("/api/v1/users/search").permitAll()

              //.requestMatchers(HttpMethod.GET, "/api/v1/users/{id}").hasAnyRole("ADMIN", "USER")
              .requestMatchers(HttpMethod.GET, "/api/v1/terms/status/{id}").hasAnyRole("ADMIN", "USER")
              .requestMatchers(HttpMethod.GET, "/api/v1/terms/latest").permitAll()
              //.requestMatchers(HttpMethod.GET, "/api/v1/users", "/api/v1/users/page/{page}", "/api/v1/users/{id}").permitAll()
              .requestMatchers(HttpMethod.GET, "/api/v1/users", "/api/v1/users/page/{page}", "/api/v1/users/{id}").hasAnyAuthority("SCOPE_read", "SCOPE_write")
              .requestMatchers(HttpMethod.GET, "/api/v1/taxpayers", "/api/v1/taxpayers/page/{page}", "/api/v1/taxpayers/{ci}", "/api/v1/taxpayers/check/{ci}").permitAll()
              .requestMatchers(HttpMethod.GET, "/api/v1/requests", "/api/v1/requests/{id}", "/api/v1/requests/status/{status}", "/api/v1/requests/user/{userId}").permitAll()

              .requestMatchers(HttpMethod.POST, "/api/v1/terms").hasRole("ADMIN")
              .requestMatchers(HttpMethod.POST, "/api/v1/terms/record").permitAll()
              .requestMatchers(HttpMethod.POST, "/api/v1/users", "/api/v1/users/registration").permitAll()                  //SOLO PRUEBAS
              .requestMatchers(HttpMethod.POST, "/api/v1/taxpayers").hasRole("ADMIN")
              .requestMatchers(HttpMethod.POST, "/api/v1/password/reset-request").permitAll()
              .requestMatchers(HttpMethod.POST, "/api/v1/password/reset").permitAll()

              .requestMatchers(HttpMethod.PUT, "/api/v1/users/*").hasRole("ADMIN")
              .requestMatchers(HttpMethod.DELETE, "/api/v1/users/*").hasRole("ADMIN")

              .requestMatchers("/api/v1/terms/*").hasRole("ADMIN")
              //.requestMatchers("/api/v1/users/*").hasRole("ADMIN")
              .requestMatchers("/api/v1/taxpayers/*").hasRole("ADMIN")
              .requestMatchers("/api/v1/procedures/*").permitAll()

              .anyRequest().authenticated())
              .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
              .csrf(csrf -> csrf.disable())
          .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())))
          .oauth2Login(oauth2Login -> oauth2Login.loginPage("http://127.0.0.1:8001/login/oauth2/code/msvc-users-prod"))
          .oauth2Client(withDefaults());
      return http.build();
  }

  @Bean
  public JwtAuthenticationConverter jwtAuthenticationConverter() {
      JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
      jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("roles");
      jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");

      JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
      jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
      return jwtAuthenticationConverter;
  }
  
}
