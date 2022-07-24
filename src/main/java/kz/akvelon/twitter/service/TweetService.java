package kz.akvelon.twitter.service;

import kz.akvelon.twitter.dto.response.tweets.TweetsDtoPage;
import kz.akvelon.twitter.model.Tweet;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TweetService extends CrudService<Tweet, Long>{
    TweetsDtoPage getTweets(Pageable pageable);

    List<Tweet> findTweetsByDescription(String text, Pageable pageable);

    Tweet createPoll(Long tweetId, Long pollDateTime, List<String> choices);

    Tweet voteInPoll(String email, Long tweetId, Long pollId, Long pollChoiceId);

    String deleteScheduledTweets(List<Long> tweetsIds);
}
