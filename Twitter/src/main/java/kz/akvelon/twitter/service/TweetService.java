package kz.akvelon.twitter.service;

import kz.akvelon.twitter.dto.response.tweets.TweetsDtoPage;
import kz.akvelon.twitter.model.Account;
import kz.akvelon.twitter.model.FeedTweets;
import kz.akvelon.twitter.model.Tag;
import kz.akvelon.twitter.model.Tweet;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TweetService extends CrudService<Tweet, Long>{
    TweetsDtoPage getTweets(Pageable pageable);


    List<Tweet> findTweetsByDescription(String text, Pageable pageable);


    Tweet createPoll(Long tweetId, Long pollDateTime, List<String> choices);

    Tweet voteInPoll(Long tweetId, Long pollId, Long pollChoiceId);

    String deleteScheduledTweets(List<Long> tweetsIds);


    boolean reaction(Long tweetId, Long reactionId);

    Tweet retweet(Long tweetId);

    Tweet quote(Long tweetId, Tweet tweet);

    void creatingTweetFeed(Long tweetId);

    void addTagToTweet(Long tweetId, String tagName);

    FeedTweets getFeed();

}
