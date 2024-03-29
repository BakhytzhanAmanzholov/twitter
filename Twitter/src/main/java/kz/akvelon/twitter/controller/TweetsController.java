package kz.akvelon.twitter.controller;

import kz.akvelon.twitter.controller.api.TweetsApi;
import kz.akvelon.twitter.dto.request.PollVoteRequestDto;
import kz.akvelon.twitter.dto.request.TweetDeleteRequestDto;
import kz.akvelon.twitter.dto.request.TweetRequestDto;
import kz.akvelon.twitter.dto.request.TweetCreatePollDto;
import kz.akvelon.twitter.dto.response.tweets.FeedDto;
import kz.akvelon.twitter.dto.response.tweets.TweetResponseDto;
import kz.akvelon.twitter.dto.response.tweets.TweetWithPollResponseDto;
import kz.akvelon.twitter.model.Account;
import kz.akvelon.twitter.model.FeedTweets;
import kz.akvelon.twitter.model.Tag;
import kz.akvelon.twitter.model.Tweet;
import kz.akvelon.twitter.service.FeedService;
import kz.akvelon.twitter.service.TagsService;
import kz.akvelon.twitter.service.TweetService;
import kz.akvelon.twitter.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.NoSuchElementException;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/tweets")
public class TweetsController implements TweetsApi {
    private final TweetService tweetService;

    @Override
    public ResponseEntity<TweetResponseDto> save(@RequestBody TweetRequestDto tweetRequest) {
        Tweet tweet = Tweet.fromRequestDto(tweetRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(TweetResponseDto.from(tweetService.save(tweet)));
    }

    @Override
    public ResponseEntity<?> findById(@PathVariable("tweet-id") Long tweetId) {
        try {
            Tweet tweet = tweetService.findById(tweetId);
            tweetService.creatingTweetFeed(tweetId);
            return ResponseEntity.status(HttpStatus.OK).body(TweetResponseDto.from(tweet));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.OK).body("No matching tweet found");
        }
    }

    @Override
    public ResponseEntity<?> retweet(@PathVariable("tweet-id") Long tweetId) {
        return ResponseEntity.ok(TweetResponseDto.from(tweetService.retweet(tweetId)));
    }

    @Override
    public ResponseEntity<?> quote(@PathVariable("tweet-id") Long tweetId, @RequestBody TweetRequestDto tweetRequestDto) {
        Tweet tweet = Tweet.fromRequestDto(tweetRequestDto);
        return ResponseEntity.ok(tweetService.quote(tweetId, tweet));
    }

    @Override
    public ResponseEntity<?> share(@PathVariable("tweet-id") Long tweetId) {
        String link = "http://localhost/tweets/" + tweetId;

        StringSelection stringSelection = new StringSelection(link);
        Toolkit defaultToolkit = Toolkit.getDefaultToolkit();

        Clipboard clipboard = defaultToolkit.getSystemClipboard();
        clipboard.setContents(stringSelection, null);

        return ResponseEntity.status(HttpStatus.OK).body("The link has been copied!");
    }

    @Override
    public ResponseEntity<?> reaction(@PathVariable("tweet-id") Long tweetId, @PathVariable("reaction-id") Long reactionId) {
        if (tweetService.reaction(tweetId, reactionId)) {
            return ResponseEntity.status(HttpStatus.OK).body("You have successfully left a reaction");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body("You have already left a reaction");
        }
    }


    @Override
    public ResponseEntity<?> addTag(Long tweetId, String tagName) {
        tweetService.addTagToTweet(tweetId, tagName);
        return ResponseEntity.status(HttpStatus.OK).body("Added the tag to the tweet");
    }

    @GetMapping("/feed")
    public ResponseEntity<FeedDto> feed(){
        FeedTweets feedTweets = tweetService.getFeed();

        return ResponseEntity.status(HttpStatus.OK).body(FeedDto.from(feedTweets));
    }

    @PostMapping("/{tweet-id}/poll")
    public ResponseEntity<?> createPoll(@RequestBody TweetCreatePollDto tweetRequest, @PathVariable("tweet-id") Long tweetId) {
        Tweet tweet = tweetService.createPoll(tweetId, tweetRequest.getPollDateTime(), tweetRequest.getChoices());

        if (tweet.getPoll() != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(TweetWithPollResponseDto.from(tweet));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No matching tweet found");
        }
    }

    @PostMapping("/vote")
    public ResponseEntity<?> voteInPoll(@RequestBody PollVoteRequestDto pollVoteRequestDto) {
        TweetResponseDto tweet = TweetResponseDto.from(tweetService.voteInPoll(
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
