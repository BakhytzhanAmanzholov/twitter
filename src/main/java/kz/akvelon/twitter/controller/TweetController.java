package kz.akvelon.twitter.controller;

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
        Account account = userService.findByEmail(userService.isLogged());
        tweet.setAccount(account);
        return ResponseEntity.status(HttpStatus.CREATED).body(TweetResponseDto.from(tweetService.save(tweet)));
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

    @PostMapping("/retweet/{tweet-id}")
    public ResponseEntity<?> retweet(@PathVariable("tweet-id") Long tweetId){
        userService.retweet(tweetId, userService.isLogged());
        return ResponseEntity.ok("Retweeted");
    }

    @PostMapping("/quote/{tweet-id}")
    public ResponseEntity<?> quote(@PathVariable("tweet-id") Long tweetId, @RequestBody TweetRequestDto tweetRequestDto){
        Tweet tweet = Tweet.fromRequestDto(tweetRequestDto);
        userService.quote(tweetId, userService.isLogged(), tweet);
        return ResponseEntity.ok("Quote tweet");
    }

    @PostMapping("/reaction/{tweet-id}/{reaction-id}")
    public ResponseEntity<?> reaction(@PathVariable("tweet-id") Long tweetId, @PathVariable("reaction-id") Long reactionId){
        if(userService.reaction(tweetId, userService.isLogged(), reactionId)){
            return ResponseEntity.status(HttpStatus.OK).body("You have successfully left a reaction");
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body("You have already left a reaction");
        }
    }
}
