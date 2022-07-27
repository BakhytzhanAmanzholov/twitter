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
        @Tag(name = "Admin panel")
})
@RequestMapping("/admin")
public interface AdminApi {
    @PostMapping("/ban")
    @Operation(summary = "Ban a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "User was successfully banned",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseEntity.class)
                            )
                    }
            ),
            @ApiResponse(responseCode = "400",
                    description = "Admin cannot ban him/herself",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseEntity.class)
                            )
                    }
            )
    })
    ResponseEntity<?> ban(@RequestBody String email);
}
