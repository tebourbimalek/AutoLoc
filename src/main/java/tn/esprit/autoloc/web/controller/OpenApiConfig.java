package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(new Info()
                .title("AutoLoc API")
                .description("API REST du cas d'étude AutoLoc - Atelier 5 : Spring MVC et SpringDoc")
                .version("1.0.0"));
    }
}
