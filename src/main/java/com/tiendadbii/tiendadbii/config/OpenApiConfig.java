package com.tiendadbii.tiendadbii.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
      .info(new Info()
        .version("v1")
        .title("Tienda REST API")
        .description("Descubre el API REST de Tienda con la documentación generada por Swagger. " +
          "Claro, conciso y sencillo de interactuar con los servicios de una tienda.\n" +
          "Simplifica la interacción de las operaciones CRUD."));
  }
}
