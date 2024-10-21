package com.azo.backend.msvc.binnacle.msvc_binnacle.services;

import org.springframework.stereotype.Service;

@Service
public class PaymentValidationService {

  public boolean validatePaymentStatus(String cedula) {
    // Lógica para consultar el estado de pagos del contribuyente
    // Simularemos que el contribuyente no tiene deudas
    return true;
  }
  
}
