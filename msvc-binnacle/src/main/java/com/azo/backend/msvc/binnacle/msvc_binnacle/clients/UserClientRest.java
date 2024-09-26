package com.azo.backend.msvc.binnacle.msvc_binnacle.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.azo.backend.msvc.binnacle.msvc_binnacle.config.FeignClientConfig;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.User;

//paso 4: comunicación con UserController de msvc_users

//@FeignClient(name = "msvc-users-prod", url = "localhost:8001/api/v1/users")
//@FeignClient(name = "msvc-users-prod", url = "host.docker.internal:8001/api/v1/users")
//@FeignClient(name = "msvc-users-prod", url = "msvc-users-prod:8001/api/v1/users")
@FeignClient(name = "msvc-users-prod", url = "${msvc.users.url}", configuration = FeignClientConfig.class)
public interface UserClientRest {
  
  @GetMapping("/{id}")
  User detail (@PathVariable Long id);

}
