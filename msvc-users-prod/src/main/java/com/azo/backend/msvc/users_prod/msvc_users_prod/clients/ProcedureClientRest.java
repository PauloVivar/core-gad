package com.azo.backend.msvc.users_prod.msvc_users_prod.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

//paso: comunicación con ProcedureController de msvc_avaluos

//@FeignClient(name = "msvc-avaluos-catastros", url = "localhost:8002/api/v1/procedures")
//@FeignClient(name = "msvc-avaluos-catastros", url = "msvc-avaluos-catastros:8002/api/v1/procedures")
@FeignClient(name = "msvc-avaluos-catastros", url = "${msvc.procedures.url}")
public interface ProcedureClientRest {
  
  @DeleteMapping("/remove-procedure-user/{id}")
  void removeProcedureUserById(@PathVariable Long id);

}
