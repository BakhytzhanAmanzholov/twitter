package kz.akvelon.twitter.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Register with email, username and password")
public class RegistrationDto {
    @Schema(name = "Email of a user", example = "email@gmail.com")
    private String email;
    @Schema(name = "Username of a user", example = "clark.jake")
    private String username;
    @Schema(name = "Password of a user", example = "qwerty123")
    private String password;
}
