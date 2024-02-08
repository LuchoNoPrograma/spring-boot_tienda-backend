package com.tiendadbii.tiendadbii;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(servers = {@Server(url = "/", description = "Servidor por defecto")})
@SpringBootApplication
public class TiendaDbiiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TiendaDbiiApplication.class, args);
	}

}
