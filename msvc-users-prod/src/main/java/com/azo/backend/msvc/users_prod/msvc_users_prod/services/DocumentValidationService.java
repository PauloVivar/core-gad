package com.azo.backend.msvc.users_prod.msvc_users_prod.services;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DocumentValidationService  {
  public boolean validateDocuments(String ci, MultipartFile ciImage, MultipartFile faceImage) throws IOException {
    // Aquí implementaría la lógica real de validación de documentos
    // Por ahora, simulamos una validación básica
    if (ciImage == null || faceImage == null) {
        return false;
    }
    
    // Simulación de validación (reemplazar con lógica real)
    return !ciImage.isEmpty() && !faceImage.isEmpty();
  }

}
