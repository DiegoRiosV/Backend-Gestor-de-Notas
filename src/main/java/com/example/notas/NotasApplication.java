package com.example.notas;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
    info = @Info(
        title = "API del Gestor de Notas",
        version = "1.0.0",
        description = "Esta API permite gestionar categorías y notas. " +
                      "Provee endpoints para crear, leer, actualizar, eliminar y buscar notas."
    )
)
@SpringBootApplication
public class NotasApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotasApplication.class, args);
	}

}
