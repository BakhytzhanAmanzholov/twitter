package kz.akvelon.twitter.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import kz.akvelon.twitter.dto.request.PageDto;
import kz.akvelon.twitter.dto.request.RegistrationDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tags(value = {
        @Tag(name = "Registration")
})
@RequestMapping
public interface RegistrationApi {

//    @GetMapping
//    @Operation(summary = "Get tweets by pagination")
//    @ApiResponses(value =
//    @ApiResponse(responseCode = "200",
//            description = "Tweets were successfully returned",
//            content = {
//                    @Content(mediaType = "application/json",
//                            schema = @Schema(implementation = ResponseEntity.class))}
//    )
//    )
//    ResponseEntity<?> index(@PageableDefault PageDto pageable);

    @PostMapping("/registration")
    @Operation(summary = "Register to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "User created successfully, then the user should confirm email",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseEntity.class))}
            ),
            @ApiResponse(responseCode = "400",
                    description = "Email is already taken by another user",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseEntity.class))}
            ),
    })
    ResponseEntity<?> registration(@RequestBody RegistrationDto registrationDto);

    @GetMapping("/registration/{email}/{username}")
    @Operation(summary = "Confirm the registration")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "User confirmed email successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseEntity.class))}
            ),
    })
    ResponseEntity<?> confirmEmail(@Parameter(description = "Email of a user", example = "email@gmail.com")
                                   @PathVariable("email") String email,
                                   @Parameter(description = "Username of a user", example = "clark.jake")
                                   @PathVariable("username") String username);
}
