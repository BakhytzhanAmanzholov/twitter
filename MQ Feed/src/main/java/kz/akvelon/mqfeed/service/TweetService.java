package kz.akvelon.mqfeed.service;

import kz.akvelon.mqfeed.models.Tweet;

import java.util.List;

public interface TweetService {
    Tweet findById(Long id);

    List<Tweet> findTweetsByTextLike(String text);
}
