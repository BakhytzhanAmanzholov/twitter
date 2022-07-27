package kz.akvelon.twitter.controller;

import kz.akvelon.twitter.controller.api.TwitterApi;
import kz.akvelon.twitter.dto.request.TweetRequestDto;
import kz.akvelon.twitter.dto.response.tweets.TweetResponseDto;
import kz.akvelon.twitter.model.Account;
import kz.akvelon.twitter.model.Tweet;
import kz.akvelon.twitter.service.TweetService;
import kz.akvelon.twitter.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.NoSuchElementException;

@RestController
@Slf4j
@RequiredArgsConstructor
public class TweetController implements TwitterApi {
    private final TweetService tweetService;
    private final UserService userService;

    @Override
    public ResponseEntity<?> save(@RequestBody TweetRequestDto tweetRequest) {
        Tweet tweet = Tweet.fromRequestDto(tweetRequest);
        Account account = userService.findByEmail(userService.isLogged());

        if (account == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Email is not registered in this system");
        }

        tweet.setAccount(account);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(TweetResponseDto.from(tweetService.save(tweet)));
    }

    @Override
    public ResponseEntity<?> findById(@PathVariable("tweet-id") Long tweetId) {
        try {
            Tweet tweet = tweetService.findById(tweetId);
            return ResponseEntity.status(HttpStatus.OK).body(TweetResponseDto.from(tweet));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No matching tweet found");
        }
    }

    @Override
    public ResponseEntity<?> retweet(@PathVariable("tweet-id") Long tweetId) {
        userService.retweet(tweetId, userService.isLogged());
        return ResponseEntity.ok("Retweeted");
    }

    @Override
    public ResponseEntity<?> quote(@PathVariable("tweet-id") Long tweetId, @RequestBody TweetRequestDto tweetRequestDto) {
        Tweet tweet = Tweet.fromRequestDto(tweetRequestDto);
        userService.quote(tweetId, userService.isLogged(), tweet);
        return ResponseEntity.ok("Quote tweet");
    }


    @Override
    public ResponseEntity<?> reaction(@PathVariable("tweet-id") Long tweetId, @PathVariable("reaction-id") Long reactionId) {
        if (userService.reaction(tweetId, userService.isLogged(), reactionId)) {
            return ResponseEntity.status(HttpStatus.OK).body("You have successfully left a reaction");
        } else {
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body("You have already left a reaction");
        }
    }

    @Override
    public ResponseEntity<?> share(@PathVariable("tweet-id") Long tweetId) {
        String link = "http://localhost:8181/tweets/" + tweetId;
        StringSelection stringSelection = new StringSelection(link);
        Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
        Clipboard clipboard = defaultToolkit.getSystemClipboard();
        clipboard.setContents(stringSelection, null);
        return ResponseEntity.status(HttpStatus.OK).body("The link has been copied!");
    }
}
