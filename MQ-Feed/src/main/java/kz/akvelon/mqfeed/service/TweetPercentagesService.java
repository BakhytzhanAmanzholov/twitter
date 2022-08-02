package kz.akvelon.mqfeed.service;

import kz.akvelon.mqfeed.models.Tweet;
import kz.akvelon.mqfeed.models.TweetPercentages;

public interface TweetPercentagesService {
    void increase(TweetPercentages tweet, Double percent);

    TweetPercentages save(TweetPercentages tweetPercentages);

}
