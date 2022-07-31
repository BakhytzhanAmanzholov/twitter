package kz.akvelon.twitter.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tags;
import kz.akvelon.twitter.dto.response.tweets.TweetResponseDto;
import kz.akvelon.twitter.model.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tags(value = {
        @io.swagger.v3.oas.annotations.tags.Tag(name = "Tags")
})
@RequestMapping("/tags")
public interface TagsApi {
    @GetMapping
    @Operation(summary = "Get list of tags")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "List of tags",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseEntity.class)
                            )
                    }
            )
    })
    ResponseEntity<List<Tag>> getTags();

    @GetMapping("/{tag-name}")
    @Operation(summary = "Find tag by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Tag with its body",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseEntity.class)
                            )
                    }
            ),
            @ApiResponse(responseCode = "400",
                    description = "Tag was not found",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseEntity.class)
                            )
                    }
            )
    })
    ResponseEntity<?> findByName(@PathVariable("tag-name") String tagName);

    @PostMapping("/{tag-name}")
    @Operation(summary = "Add tag")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Added tag with ID",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseEntity.class)
                            )
                    }
            ),
            @ApiResponse(responseCode = "400",
                    description = "Tag is already exists",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseEntity.class)
                            )
                    }
            )
    })
    ResponseEntity<?> addTag(@RequestBody String name);

    @GetMapping("/{tag-name}/tweets")
    @Operation(summary = "Get tweets by tag name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Tweets by tag name",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseEntity.class)
                            )
                    }
            )
    })
    ResponseEntity<List<TweetResponseDto>> getTweetsByTag(@PathVariable("tag-name") String tagName);
}
