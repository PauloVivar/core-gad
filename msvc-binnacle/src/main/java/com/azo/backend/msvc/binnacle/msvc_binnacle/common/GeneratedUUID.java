package com.azo.backend.msvc.binnacle.msvc_binnacle.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.hibernate.annotations.IdGeneratorType;

// Anotación personalizada que usa IdGeneratorType
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@IdGeneratorType(UUIDGenerator.class)
public @interface GeneratedUUID {
  
}
