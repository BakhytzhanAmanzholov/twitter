package kz.akvelon.twitter.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Login with email and password")
public class LoginDto {
    @Schema(name = "Email of a user", example = "email@gmail.com")
    private String email;
    @Schema(name = "Password of a user", example = "qwerty123")
    private String password;
}
