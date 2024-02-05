package com.tiendadbii.tiendadbii.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate6.Hibernate6Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {
  @Bean
  ObjectMapper objectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    //Configuración para poder serializar el paquete java.time
    objectMapper.registerModule(new JavaTimeModule());

    Hibernate6Module hibernate6Module = new Hibernate6Module();
    hibernate6Module.enable(Hibernate6Module.Feature.USE_TRANSIENT_ANNOTATION);
    hibernate6Module.enable(Hibernate6Module.Feature.FORCE_LAZY_LOADING);
    //Configuración para poder ignorar campos con fetch=FetchType.LAZY
    objectMapper.registerModule(new Hibernate6Module());
    objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    return objectMapper;
  }
}
