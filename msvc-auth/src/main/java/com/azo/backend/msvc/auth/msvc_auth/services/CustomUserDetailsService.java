package com.azo.backend.msvc.auth.msvc_auth.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.azo.backend.msvc.auth.msvc_auth.clients.UserClientRest;
import com.azo.backend.msvc.auth.msvc_auth.models.User;

import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService  {

  @Autowired
  private UserClientRest userClientRest;

  private Logger log = LoggerFactory.getLogger(CustomUserDetailsService.class);

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      
    try {
      User user = userClientRest.findByUsername(username);
      if (user == null) {
          throw new UsernameNotFoundException("Usuario no encontrado: " + username );
      }

      log.info("Usuario Login", user.getUsername());
      log.info("Usuario Email", user.getEmail());
      log.info("Usuario Roles", user.getRoles());
      
      return new org.springframework.security.core.userdetails.User(
          user.getUsername(),
          user.getPassword(),
          user.getRoles().stream()
              .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
              .collect(Collectors.toList())
      );
      
    } catch (RuntimeException e) {
      String error = "Error en el Login, no existe el Usuario: " + username + 
      " en el sistema.";
      log.error(error, e);
      log.error(e.getMessage());
      throw new UsernameNotFoundException(error);
    }
    
  }
  
}
