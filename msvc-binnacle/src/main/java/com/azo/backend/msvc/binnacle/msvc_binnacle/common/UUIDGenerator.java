package com.azo.backend.msvc.binnacle.msvc_binnacle.common;

import java.util.EnumSet;
import java.util.UUID;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.generator.BeforeExecutionGenerator;
import org.hibernate.generator.EventType;

public class UUIDGenerator implements BeforeExecutionGenerator {

  // Definir los tipos de eventos en los que se usará el generador (por ejemplo, antes de insertar una entidad)
  @Override
  public EnumSet<EventType> getEventTypes() {
    // Este generador se utilizará solo durante la inserción (creación de nuevas entidades)
    return EnumSet.of(EventType.INSERT);
  }

  // Lógica para generar el UUID
  @Override
  public Object generate(SharedSessionContractImplementor session, Object owner, Object currentValue,
      EventType eventType) {
    // Si ya hay un valor actual, devuélvelo, de lo contrario genera uno nuevo
    if (currentValue == null) {
        return UUID.randomUUID();  // Genera un UUID aleatorio (UUID v4)
    }
    return currentValue;  // Si ya hay un valor asignado, lo conservamos
  }

}
