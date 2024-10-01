package com.azo.backend.msvc.binnacle.msvc_binnacle.factories;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.azo.backend.msvc.binnacle.msvc_binnacle.enums.RequestStatus;
import com.azo.backend.msvc.binnacle.msvc_binnacle.enums.RequestType;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.entities.Request;

@Component
public abstract class RequestFactory {

  public Request createRequest(RequestType requestType) {
    Request request = createSpecificRequest(requestType);
    request.setEntryDate(LocalDateTime.now());
    request.setStatus(RequestStatus.INGRESADO);
    request.setType(requestType);
    return request;
  }

  protected abstract Request createSpecificRequest(RequestType requestType);

}
