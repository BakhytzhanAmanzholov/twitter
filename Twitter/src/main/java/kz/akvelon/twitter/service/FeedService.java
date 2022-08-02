package kz.akvelon.twitter.service;

import kz.akvelon.twitter.model.Account;
import kz.akvelon.twitter.model.FeedTweets;

public interface FeedService {
    void creatingTweetFeed(String email, Long tweetID);

    FeedTweets findByAccount(Account account);

    FeedTweets save(FeedTweets feedTweets);
}
