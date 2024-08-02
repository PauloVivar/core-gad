package com.azo.backend.msvc.users_bck.msvc_users_bck.services;

//3. Tercero Create EmailService -> Implementación del CRUD

public interface EmailService {

  //sendPasswordResetEmail
  void sendPasswordResetEmail(String to, String code);
  
}
