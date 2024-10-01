package com.azo.backend.msvc.binnacle.msvc_binnacle.factories;

import org.springframework.stereotype.Component;

import com.azo.backend.msvc.binnacle.msvc_binnacle.enums.RequestType;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.entities.Request;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.entities.RequestCadastralRecord;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.entities.RequestSubdivisionCertificate;

@Component
public class ConcreteRequestFactory extends RequestFactory {

  @Override
  protected Request createSpecificRequest(RequestType requestType) {
    switch (requestType) {
        case FICHA_CATASTRAL:
            return new RequestCadastralRecord();
        case CERTIFICADO_FRACCIONAMIENTO:
            return new RequestSubdivisionCertificate();
        default:
            throw new IllegalArgumentException("Tipo de solicitud desconocido: " + requestType);
    }
  }
  
}
