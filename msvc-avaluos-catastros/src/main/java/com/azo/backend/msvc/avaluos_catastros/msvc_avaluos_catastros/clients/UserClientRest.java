package com.azo.backend.msvc.avaluos_catastros.msvc_avaluos_catastros.clients;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.azo.backend.msvc.avaluos_catastros.msvc_avaluos_catastros.models.User;

//paso 4: comunicación con UserController de msvc_users

@FeignClient(name = "msvc-users-prod", url = "localhost:8003/api/v1/users")
public interface UserClientRest {
  
  @GetMapping("/{id}")
  User detail (@PathVariable Long id);

  //@PostMapping("/")
  @PostMapping
  User create (@RequestBody User user);

  @GetMapping("/users-by-procedure")
  List<User> getUsersByProcedure(@RequestParam Iterable<Long> ids);

}
