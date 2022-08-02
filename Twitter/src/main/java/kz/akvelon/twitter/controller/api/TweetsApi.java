package kz.akvelon.twitter.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import kz.akvelon.twitter.dto.request.PollVoteRequestDto;
import kz.akvelon.twitter.dto.request.TweetDeleteRequestDto;
import kz.akvelon.twitter.dto.request.TweetRequestDto;
import kz.akvelon.twitter.dto.request.TweetCreatePollDto;
import kz.akvelon.twitter.dto.response.tweets.TweetResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tags(value = {
        @Tag(name = "Tweets")
})
@RequestMapping("/tweets")
public interface TweetsApi {
    @PostMapping
    @Operation(summary = "Save a tweet")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Tweet has been saved",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseEntity.class)
                            )
                    }
            ),
            @ApiResponse(responseCode = "400",
                    description = "Email is not registered in system",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseEntity.class)
                            )
                    }
            )
    })
    ResponseEntity<?> save(@RequestBody TweetRequestDto tweetRequest);

    @GetMapping("/{tweet-id}")
    @Operation(summary = "Find a tweet by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Returned tweet with its body",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseEntity.class)
                            )
                    }
            ),
            @ApiResponse(responseCode = "404",
                    description = "Tweet was not found",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseEntity.class)
                            )
                    }
            )
    })
    ResponseEntity<?> findById(@Parameter(name = "Tweet id", example = "1") @PathVariable("tweet-id") Long tweetId);

    @PostMapping("/{tweet-id}/retweet")
    @Operation(summary = "Retweet a tweet")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Retweeted a tweet",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseEntity.class)
                            )
                    }
            )
    })
    ResponseEntity<?> retweet(@PathVariable("tweet-id") Long tweetId);

    @PostMapping("/{tweet-id}/quote")
    @Operation(summary = "Quote a tweet")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Quoted a tweet",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseEntity.class)
                            )
                    }
            )
    })
    ResponseEntity<?> quote(@PathVariable("tweet-id") Long tweetId, @RequestBody TweetRequestDto tweetRequestDto);

    @PostMapping("/{tweet-id}/reactions/{reaction-id}")
    @Operation(summary = "React to a tweet")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Reacted to a tweet",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseEntity.class)
                            )
                    }
            ),
            @ApiResponse(responseCode = "208",
                    description = "Already has reacted to a tweet",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseEntity.class)
                            )
                    }
            )
    })
    ResponseEntity<?> reaction(@PathVariable("tweet-id") Long tweetId, @PathVariable("reaction-id") Long reactionId);

    @GetMapping("/{tweet-id}/share")
    @Operation(summary = "Share a tweet")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Successfully shared a tweet",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseEntity.class)
                            )
                    }
            )
    })
    ResponseEntity<?> share(@PathVariable("tweet-id") Long tweetId);

    @PostMapping("/{tweet-id}/tags/{tag-name}")
    @Operation(summary = "Add a tag to a tweet")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Successfully added a tweet",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseEntity.class)
                            )
                    }
            ),
            @ApiResponse(responseCode = "400",
                    description = "Tweet or tag is not found",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseEntity.class)
                            )
                    }
            )
    })
    ResponseEntity<?> addTag(@PathVariable("tweet-id") Long tweetId, @PathVariable("tag-name") String tagName);

    @PostMapping("/{tweet-id}/poll")
    @Operation(summary = "Add a poll to a tweet")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Successfully added a poll",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseEntity.class)
                            )
                    }
            ),
            @ApiResponse(responseCode = "400",
                    description = "Tweet is not found",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseEntity.class)
                            )
                    }
            )
    })
    ResponseEntity<?> createPoll(@RequestBody TweetCreatePollDto tweetRequest, @PathVariable("tweet-id") Long tweetId);

    @PostMapping("/vote")
    @Operation(summary = "Vote in a poll")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Returned a tweet",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseEntity.class)
                            )
                    }
            )
    })
    ResponseEntity<?> voteInPoll(@RequestBody PollVoteRequestDto pollVoteRequestDto);

    @PostMapping("/schedule")
    @Operation(summary = "Schedule a date time for a tweet")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Returned a tweet",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseEntity.class)
                            )
                    }
            )
    })
    ResponseEntity<TweetResponseDto> createScheduledTweet(@RequestBody TweetRequestDto tweetRequest);

    @PutMapping("/schedule")
    @Operation(summary = "Update a scheduled date time for a tweet")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Returned a tweet",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseEntity.class)
                            )
                    }
            )
    })
    ResponseEntity<TweetResponseDto> updateScheduledTweet(@RequestBody TweetRequestDto tweetRequest);

    @DeleteMapping("/schedule")
    @PutMapping("/schedule")
    @Operation(summary = "Delete a scheduled date time for a tweet")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Returned a tweet",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseEntity.class)
                            )
                    }
            )
    })
    ResponseEntity<String> deleteScheduledTweets(@RequestBody TweetDeleteRequestDto tweetRequest);
}
