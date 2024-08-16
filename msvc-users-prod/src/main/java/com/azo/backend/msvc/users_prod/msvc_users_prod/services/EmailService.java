package com.azo.backend.msvc.users_prod.msvc_users_prod.services;

//3. Tercero Create EmailService -> Implementación del CRUD

public interface EmailService {

  //sendPasswordResetEmail
  void sendPasswordResetEmail(String to, String code);
  
}
