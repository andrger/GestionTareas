package com.gestordetareas.GestionDeTareas.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Gestión de Tareas API")
                        .version("v0.0.1")
                        .description("API para la gestión de tareas, usuarios y asignaciones"));
    }
}

