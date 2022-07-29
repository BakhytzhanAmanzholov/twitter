package kz.akvelon.twitter.controller;

import kz.akvelon.twitter.controller.api.TweetsApi;
import kz.akvelon.twitter.dto.request.TweetRequestDto;
import kz.akvelon.twitter.dto.response.tweets.TweetResponseDto;
import kz.akvelon.twitter.model.Account;
import kz.akvelon.twitter.model.Tag;
import kz.akvelon.twitter.model.Tweet;
import kz.akvelon.twitter.service.TagsService;
import kz.akvelon.twitter.service.TweetService;
import kz.akvelon.twitter.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.NoSuchElementException;

@RestController
@Slf4j
@RequiredArgsConstructor
public class TweetsController implements TweetsApi {
    private final TweetService tweetService;
    private final TagsService tagsService;
    private final UserService userService;

    @Override
    public ResponseEntity<?> save(TweetRequestDto tweetRequest) {
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
    public ResponseEntity<?> findById(Long tweetId) {
        try {
            Tweet tweet = tweetService.findById(tweetId);
            return ResponseEntity.status(HttpStatus.OK).body(TweetResponseDto.from(tweet));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No matching tweet found");
        }
    }

    @Override
    public ResponseEntity<?> retweet(Long tweetId) {
        userService.retweet(tweetId, userService.isLogged());
        return ResponseEntity.ok("Retweeted");
    }

    @Override
    public ResponseEntity<?> quote(Long tweetId, TweetRequestDto tweetRequestDto) {
        Tweet tweet = Tweet.fromRequestDto(tweetRequestDto);
        userService.quote(tweetId, userService.isLogged(), tweet);
        return ResponseEntity.ok("Quote tweet");
    }


    @Override
    public ResponseEntity<?> reaction(Long tweetId, Long reactionId) {
        if (userService.reaction(tweetId, userService.isLogged(), reactionId)) {
            return ResponseEntity.status(HttpStatus.OK).body("You have successfully left a reaction");
        } else {
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body("You have already left a reaction");
        }
    }

    @Override
    public ResponseEntity<?> share(Long tweetId) {
        String link = "http://localhost:8080/tweets/" + tweetId;
        StringSelection stringSelection = new StringSelection(link);
        Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
        Clipboard clipboard = defaultToolkit.getSystemClipboard();
        clipboard.setContents(stringSelection, null);
        return ResponseEntity.status(HttpStatus.OK).body("The link has been copied!");
    }

    @Override
    public ResponseEntity<?> addTag(Long tweetId, String tagName) {
        Tweet tweet = tweetService.findById(tweetId);
        Tag tag = tagsService.findByName(tagName);

        if (tweet == null || tag == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tweet or tag is not found");
        }

        tweetService.addTag(tweet, tag);

        return ResponseEntity.status(HttpStatus.OK).body("Added the tag to the tweet");
    }
}
