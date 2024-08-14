package com.azo.backend.msvc.avaluos_catastros.msvc_avaluos_catastros.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.azo.backend.msvc.avaluos_catastros.msvc_avaluos_catastros.models.Customer;

@FeignClient(name = "msvc-users-back", url = "localhost:8001/api/v1/customers")
public interface CustomerClientRest {

  @GetMapping("/{id}")
  Customer detail (@PathVariable Long id);

  @PostMapping("/")
  Customer create (@RequestBody Customer customer);

}
