package kz.akvelon.twitter.config.api;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import static kz.akvelon.twitter.config.api.OpenApiCustoms.*;

@Configuration
public class OpenApiConfig {
    @Bean
    @Profile("noSecurity")
    @Primary
    public OpenAPI openApiWithoutSecurity() {
        return new OpenAPI()
                .paths(buildAuthenticationPath())
                .components(new Components()
                        .addSchemas("EmailAndPassword", emailAndPassword())
                        .addSchemas("Tokens", tokens()))
                .info(buildInfo());
    }

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .addSecurityItem(buildSecurity())
                .paths(buildAuthenticationPath())
                .components(new Components()
                        .addSchemas("EmailAndPassword", emailAndPassword())
                        .addSchemas("Tokens", tokens())
                        .addSecuritySchemes("bearerAuth", securityScheme()))
                .info(buildInfo());
    }
}
