package com.azo.backend.msvc.auth.msvc_auth.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.azo.backend.msvc.auth.msvc_auth.models.User;

@FeignClient(name = "msvc-users-prod", url = "${msvc.users.url}")
public class UserClientRest {

  @GetMapping("/{id}")
  User detail(@PathVariable Long id);

  @GetMapping("/api/v1/users/search")
  public
  User findByUsername(@RequestParam String username);

}
