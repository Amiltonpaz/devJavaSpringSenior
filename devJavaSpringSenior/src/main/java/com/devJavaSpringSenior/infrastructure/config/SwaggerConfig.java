package com.devJavaSpringSenior.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.info.License;

@OpenAPIDefinition(
	    info = @Info(title = "API de Contas", version = "1.0", description = "Documentação da API de Contas"),
	    security = @SecurityRequirement(name = "bearerAuth")
	)
	@SecurityScheme(
	    name = "bearerAuth",
	    type = SecuritySchemeType.HTTP,
	    scheme = "bearer",
	    bearerFormat = "JWT"
	)
@Configuration
public class SwaggerConfig {

	 @Bean
	    public OpenAPI customOpenAPI() {
	        return new OpenAPI();    
	                        
	                        
	    }
	
}
