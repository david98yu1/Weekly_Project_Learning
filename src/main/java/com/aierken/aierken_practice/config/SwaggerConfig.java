package com.aierken.aierken_practice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI aierkenPracticeOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Aierken Practice Swagger API")
                        .description("Swagger API documentation for Aierken Practice project")
                        .version("1.0.0"));
    }
}
