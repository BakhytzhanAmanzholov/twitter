package kz.akvelon.twitter.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tags(value = {
        @Tag(name = "Roles")}
)
@RequestMapping("/roles")
public interface RoleApi {

    @PostMapping
    @Operation(summary = "Save the role of a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Role created successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseEntity.class))}
            ),
            @ApiResponse(responseCode = "208",
                    description = "Role is already created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseEntity.class))
                    }
            )
    })
    ResponseEntity<?> save(@RequestBody String name);
}
