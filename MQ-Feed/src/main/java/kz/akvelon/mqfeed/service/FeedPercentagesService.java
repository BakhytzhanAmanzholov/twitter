package kz.akvelon.mqfeed.service;

import kz.akvelon.mqfeed.models.FeedTweets;
import kz.akvelon.mqfeed.models.Tweet;

public interface FeedPercentagesService {
    FeedTweets save(FeedTweets feedTweets);

    void addTweetsToFeed(FeedTweets feedTweets, Tweet tweet);

    void sorting(FeedTweets feed);

    FeedTweets findById(Long id);

    void addTweet(Tweet tweet, FeedTweets feedTweets);
}
