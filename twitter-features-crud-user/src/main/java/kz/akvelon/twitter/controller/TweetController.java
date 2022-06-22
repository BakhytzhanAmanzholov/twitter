package kz.akvelon.twitter.controller;

import kz.akvelon.twitter.dto.TweetRequestDto;
import kz.akvelon.twitter.dto.TweetResponseDto;
import kz.akvelon.twitter.dto.TweetsDtoPage;
import kz.akvelon.twitter.model.Tweet;
import kz.akvelon.twitter.service.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tweets")
public class TweetController {
    private final TweetService tweetService;

    @GetMapping
    public ResponseEntity<TweetsDtoPage> getTweets(@PageableDefault() Pageable pageable) {
        return ResponseEntity.ok().body(tweetService.getTweets(pageable));
    }

    @PostMapping
    public ResponseEntity<TweetResponseDto> createTweet(@RequestBody TweetRequestDto tweetRequest) {
        Tweet tweet = Tweet.fromRequestDto(tweetRequest);
        return ResponseEntity.ok(TweetResponseDto.from(tweet));
    }

    @GetMapping("/{tweet-id}")
    public ResponseEntity<Tweet> getTweetById(@PathVariable("tweet-id") Long tweetId) {
        return ResponseEntity.ok(tweetService.getTweetById(tweetId));
    }

}
