package kz.akvelon.twitter.config.api;
import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static kz.akvelon.twitter.constants.GlobalApplicationConstants.AUTHENTICATION_URL;
import static kz.akvelon.twitter.security.filters.JwtAuthenticationFilter.USERNAME_PARAMETER;



@Configuration
public class OpenApiCustoms {
    public static final String BEARER_AUTH_SCHEMA_NAME = "bearerAuth";

    static Paths buildAuthenticationPath() {
        return new Paths()
                .addPathItem(AUTHENTICATION_URL, buildAuthenticationPathItem());
    }

    private static PathItem buildAuthenticationPathItem() {
        return new PathItem().post(
                new Operation()
                        .addTagsItem("Authentication")
                        .requestBody(buildAuthenticationRequestBody())
                        .responses(buildAuthenticationResponses()));

    }

    static RequestBody buildAuthenticationRequestBody() {
        return new RequestBody().content(
                new Content()
                        .addMediaType("application/x-www-form-urlencoded",
                                new MediaType()
                                        .schema(new Schema<>()
                                                .$ref("EmailAndPassword"))));
    }

    static ApiResponses buildAuthenticationResponses() {
        return new ApiResponses()
                .addApiResponse("200",
                        new ApiResponse()
                                .content(new Content()
                                        .addMediaType("application/json",
                                                new MediaType()
                                                        .schema(new Schema<>()
                                                                .$ref("Tokens")))));
    }

    static SecurityRequirement buildSecurity() {
        return new SecurityRequirement().addList(BEARER_AUTH_SCHEMA_NAME);
    }

    static Info buildInfo() {
        return new Info().title("Twitter API").version("1.0").description("REST API for Twitter");
    }

    static Schema<?> emailAndPassword() {
        return new Schema<>()
                .type("object")
                .description("Email и пароль пользователя")
                .addProperties(USERNAME_PARAMETER, new Schema<>().type("string"))
                .addProperties("password", new Schema<>().type("string"));
    }

    static Schema<?> tokens() {
        return new Schema<>()
                .type("object")
                .description("Access и Refresh токены")
                .addProperties("accessToken", new Schema<>().type("string").description("Токен доступа"))
                .addProperties("refreshToken", new Schema<>().type("string").description("Токен для обновления"));
    }

    static SecurityScheme securityScheme() {
        return new SecurityScheme()
                .name("bearerAuth")
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");
    }
}
