package kz.akvelon.twitter.service;

import kz.akvelon.twitter.dto.response.tweets.TweetsDtoPage;
import kz.akvelon.twitter.model.Account;
import kz.akvelon.twitter.model.Tweet;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TweetService extends CrudService<Tweet, Long>{
    TweetsDtoPage getTweets(Pageable pageable);

//    Tweet getTweetById(Long tweetId);
//
//    Tweet createTweet(Tweet tweet);
//
//    Tweet deleteTweet(Long accountId, Long tweetId);

//    Tweet delete(Long accountId, Long tweetId);

    List<Tweet> findTweetsByDescription(String text, Pageable pageable);

}
