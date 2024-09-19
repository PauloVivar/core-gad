package com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.mapper;

import com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.DocumentDto;
import com.azo.backend.msvc.binnacle.msvc_binnacle.models.entities.Document;

public class DtoMapperDocument {

  //Patron de diseño builder -> constructor que solo se puede intanciar con el metodo builder()

  private Document document;

    private DtoMapperDocument() {
    }

    public static DtoMapperDocument builder() {
      return new DtoMapperDocument();
    }

    public DtoMapperDocument setDocument(Document document) {
      this.document = document;
      return this;
    }

    public DocumentDto build() {
      if(document == null) {
        throw new RuntimeException("Debe pasar el Entity Document!");
      }
      return new DocumentDto(
        document.getId(),
        document.getType(),
        document.getUploadDate(),
        document.getFileUrl(),
        document.getRequest()
      );
    }
}
