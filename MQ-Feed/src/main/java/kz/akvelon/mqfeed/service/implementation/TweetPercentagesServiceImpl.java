package kz.akvelon.mqfeed.service.implementation;

import kz.akvelon.mqfeed.models.Tweet;
import kz.akvelon.mqfeed.models.TweetPercentages;
import kz.akvelon.mqfeed.repository.TweetPercentagesRepository;
import kz.akvelon.mqfeed.service.TweetPercentagesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TweetPercentagesServiceImpl implements TweetPercentagesService {

    private final TweetPercentagesRepository repository;

    @Override
    public void increase(TweetPercentages tweet, Double percent) {
        tweet.setPercent(tweet.getPercent() + percent);
    }

    @Override
    public TweetPercentages save(TweetPercentages tweetPercentages) {
        return repository.save(tweetPercentages);
    }
}
