package com.azo.backend.msvc.users_bck.msvc_users_bck.auth;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

//Clase abstracta
public abstract class SimpleGrantedAuthorityJsonCreator {

  @JsonCreator
  public SimpleGrantedAuthorityJsonCreator(@JsonProperty("authority") String role) {
  }
  
}