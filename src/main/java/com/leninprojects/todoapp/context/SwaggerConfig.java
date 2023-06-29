package com.leninprojects.todoapp.context;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("ToDo API")
                        .version("1.0")
                        .description("Sample Todo app Spring Boot 3 with Swagger")
                        .termsOfService("http://www.apache.org/licenses/LICENSE-2.0")
                        .license(new License()));
    }
}
