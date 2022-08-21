package kz.akvelon.mqfeed.service.implementation;

import kz.akvelon.mqfeed.models.FeedTweets;
import kz.akvelon.mqfeed.models.Tweet;
import kz.akvelon.mqfeed.models.TweetPercentages;
import kz.akvelon.mqfeed.repository.FeedPercentagesRepository;
import kz.akvelon.mqfeed.service.FeedPercentagesService;
import kz.akvelon.mqfeed.service.TweetPercentagesService;
import kz.akvelon.mqfeed.service.TweetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class FeedPercentagesServiceImpl implements FeedPercentagesService {

    private final FeedPercentagesRepository repository;

    private final TweetPercentagesService tweetPercentagesService;

    private final TweetService tweetService;

    @Override
    public FeedTweets save(FeedTweets feedTweets) {
        return repository.save(feedTweets);
    }

    @Override
    public void addTweetsToFeed(FeedTweets feed, Tweet tweet) {
        List<Tweet> tweets = tweetService.findTweetsByTextLike(tweet.getText());

        for (Tweet tweetLike : tweets) {
            if (feed.getTweets().contains(tweetLike)) {
                for(TweetPercentages tweetPercentages: feed.getTweetList()){
                    if(tweetPercentages.getTweet().equals(tweetLike)){
                        tweetPercentagesService.increase(tweetPercentages, 0.01);
                    }
                }
            } else {
                addTweet(tweetLike, feed);
            }
        }
    }

    @Override
    public FeedTweets findById(Long id) {
        return repository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public void sorting(FeedTweets feed) {
        Arrays.sort(feed.getTweetList().toArray());
    }

    @Override
    public void addTweet(Tweet tweet, FeedTweets feedTweets) {
        Tweet tweet1 = tweetService.findById(tweet.getId());
        FeedTweets feedTweets1 = findById(feedTweets.getId());

        feedTweets1.getTweets().add(tweet1);
        feedTweets1.getTweetList().add(
                tweetPercentagesService.save(
                        TweetPercentages.builder()
                                .tweet(tweet1)
                                .percent(0.01)
                                .build()
                )
        );
    }
}
