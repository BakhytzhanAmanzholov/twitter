package kz.akvelon.mqfeed.service.implementation;

import kz.akvelon.mqfeed.models.Tweet;
import kz.akvelon.mqfeed.repository.TweetRepository;
import kz.akvelon.mqfeed.service.TweetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TweetServiceImpl implements TweetService {
    private final TweetRepository tweetRepository;

    @Override
    public Tweet findById(Long id) {
        return tweetRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public List<Tweet> findTweetsByTextLike(String text) {
        String[] texts = text.split(" ");
        List<Tweet> tweets = new ArrayList<>();
        for (String t: texts) {
            tweets.addAll(tweetRepository.findByTextLike(t));
        }
        return tweets;
    }
}
