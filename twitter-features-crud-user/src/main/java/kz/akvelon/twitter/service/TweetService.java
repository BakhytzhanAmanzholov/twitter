package kz.akvelon.twitter.service;

import kz.akvelon.twitter.dto.TweetsDtoPage;
import kz.akvelon.twitter.model.Tweet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TweetService {
    TweetsDtoPage getTweets(Pageable pageable);

    Tweet getTweetById(Long tweetId);

    Tweet createTweet(Tweet tweet);

    Tweet deleteTweet(Long accountId, Long tweetId);

    List<Tweet> findTweetsByDescription(String text, Pageable pageable);
}
