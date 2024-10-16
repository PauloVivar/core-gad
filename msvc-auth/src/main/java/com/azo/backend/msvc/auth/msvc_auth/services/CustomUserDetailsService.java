package com.azo.backend.msvc.auth.msvc_auth.services;

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

  @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userClientRest.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        return new org.springframework.security.core.userdetails.User(
            user.getUsername(),
            user.getPassword(),
            user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                .collect(Collectors.toList())
        );
    }
  
}
