package kz.akvelon.twitter.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Update a user by username")
public class UserUpdateDto {
    @Schema(name = "Username of a user", example = "clark.jake")
    private String username;
}
