package kz.akvelon.twitter.controller;

import kz.akvelon.twitter.dto.request.PollVoteRequestDto;
import kz.akvelon.twitter.dto.request.TweetDeleteRequestDto;
import kz.akvelon.twitter.dto.request.TweetRequestDto;
import kz.akvelon.twitter.dto.response.tweets.TweetResponseDto;
import kz.akvelon.twitter.dto.response.tweets.TweetsDtoPage;
import kz.akvelon.twitter.model.Account;
import kz.akvelon.twitter.model.Tweet;
import kz.akvelon.twitter.service.TweetService;
import kz.akvelon.twitter.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.nio.file.AccessDeniedException;
import java.util.NoSuchElementException;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/tweets")
public class TweetController {
    private final TweetService tweetService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<TweetsDtoPage> findAll(@PageableDefault() Pageable pageable) {
        return ResponseEntity.status(HttpStatus.FOUND).body(tweetService.getTweets(pageable));
    }

    @PostMapping
    public ResponseEntity<TweetResponseDto> save(@RequestBody TweetRequestDto tweetRequest) {
        Tweet tweet = Tweet.fromRequestDto(tweetRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(TweetResponseDto.from(tweetService.save(tweet)));
    }

    @GetMapping("/{tweet-id}")
    public ResponseEntity<?> findById(@PathVariable("tweet-id") Long tweetId) {
        try {
            Tweet tweet = tweetService.findById(tweetId);
            return ResponseEntity.status(HttpStatus.FOUND).body(TweetResponseDto.from(tweet));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No matching tweet found");
        }
    }

    @DeleteMapping("/{tweet-id}")
    public ResponseEntity<?> deleteTweet(@PathVariable("tweet-id") Long tweetId) {
        try {
            tweetService.delete(tweetId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/retweet/{tweet-id}")
    public ResponseEntity<?> retweet(@PathVariable("tweet-id") Long tweetId) {
        userService.retweet(tweetId, userService.isLogged());
        return ResponseEntity.ok("Retweeted");
    }

    @PostMapping("/quote/{tweet-id}")
    public ResponseEntity<?> quote(@PathVariable("tweet-id") Long tweetId, @RequestBody TweetRequestDto tweetRequestDto) {
        Tweet tweet = Tweet.fromRequestDto(tweetRequestDto);
        userService.quote(tweetId, userService.isLogged(), tweet);
        return ResponseEntity.ok("Quote tweet");
    }

    @PostMapping("/reaction/{tweet-id}/{reaction-id}")
    public ResponseEntity<?> reaction(@PathVariable("tweet-id") Long tweetId, @PathVariable("reaction-id") Long reactionId) {
        if (userService.reaction(tweetId, userService.isLogged(), reactionId)) {
            return ResponseEntity.status(HttpStatus.OK).body("You have successfully left a reaction");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body("You have already left a reaction");
        }
    }

    @PostMapping("/{tweet-id}/poll")
    public ResponseEntity<?> createPoll(@RequestBody TweetRequestDto tweetRequest, @PathVariable("tweet-id") Long tweetId) {
        Tweet tweet = tweetService.createPoll(tweetId, tweetRequest.getPollDateTime(), tweetRequest.getChoices());
        if (tweet.getPoll() != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(TweetResponseDto.from(tweet));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No matching tweet found");
        }
    }

    @PostMapping("/vote")
    public ResponseEntity<?> voteInPoll(@RequestBody PollVoteRequestDto pollVoteRequestDto) {
        TweetResponseDto tweet = TweetResponseDto.from(tweetService.voteInPoll(pollVoteRequestDto.getEmail(),
                pollVoteRequestDto.getTweetId(),
                pollVoteRequestDto.getPollId(),
                pollVoteRequestDto.getPollChoiceId()));

        return ResponseEntity.ok(tweet);
    }

    @PostMapping("/schedule")
    public ResponseEntity<TweetResponseDto> createScheduledTweet(@RequestBody TweetRequestDto tweetRequest) {
        Tweet tweet = Tweet.fromRequestDto(tweetRequest);
        return ResponseEntity.ok(TweetResponseDto.from(tweetService.save(tweet)));
    }

    @PutMapping("/schedule")
    public ResponseEntity<TweetResponseDto> updateScheduledTweet(@RequestBody TweetRequestDto tweetRequest) {
        Tweet tweet = Tweet.fromRequestDto(tweetRequest);
        return ResponseEntity.ok(TweetResponseDto.from(tweetService.save(tweet)));
    }

    @DeleteMapping("/schedule")
    public ResponseEntity<String> deleteScheduledTweets(@RequestBody TweetDeleteRequestDto tweetRequest) {
        return ResponseEntity.ok(tweetService.deleteScheduledTweets(tweetRequest.getTweetIds()));
    }
}
