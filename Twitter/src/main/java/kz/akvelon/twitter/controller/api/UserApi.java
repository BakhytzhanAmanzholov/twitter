package kz.akvelon.twitter.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import kz.akvelon.twitter.dto.request.UserUpdateDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tags(value = {
        @Tag(name = "User")
})
@RequestMapping("/users")
public interface UserApi {

    @Operation(summary = "Get the profile of the user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Profile of the user",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))}
            ),
    })
    @GetMapping
    ResponseEntity<?> profile();

    @Operation(summary = "Delete the user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "User successfully deleted",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))}
            ),
    })
    @DeleteMapping
    ResponseEntity<?> delete();

    @Operation(summary = "Edit the user data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "User is updated",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))}
            ),
            @ApiResponse(responseCode = "400", description = "User is not found",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseEntity.class))
                    }
            )

    })
    @PutMapping
    ResponseEntity<?> edit(@RequestBody UserUpdateDto userUpdateDto);

    @Operation(summary = "Subscribe to another user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "User is subscribed",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))}
            ),
            @ApiResponse(responseCode = "208",
                    description = "User already has been subscribed",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseEntity.class))
                    }
            )

    })
    @PostMapping("/subscribe/{user-id}")
    ResponseEntity<?> subscribe(@Parameter(description = "ID of a user", example = "1") @PathVariable("user-id") Long id);
}
