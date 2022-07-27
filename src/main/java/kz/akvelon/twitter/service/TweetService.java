package kz.akvelon.twitter.service;

import kz.akvelon.twitter.dto.response.tweets.TweetsDtoPage;
import kz.akvelon.twitter.model.Tag;
import kz.akvelon.twitter.model.Tweet;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TweetService extends CrudService<Tweet, Long>{
    TweetsDtoPage getTweets(Pageable pageable);

    List<Tweet> findTweetsByDescription(String text, Pageable pageable);

    void addTag(Tweet tweet, Tag tag);
}
