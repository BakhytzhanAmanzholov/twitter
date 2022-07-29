package kz.akvelon.twitter.service.implementation;

import kz.akvelon.twitter.dto.response.tweets.TweetResponseDto;
import kz.akvelon.twitter.dto.response.tweets.TweetsDtoPage;
import kz.akvelon.twitter.model.Account;
import kz.akvelon.twitter.model.Tag;
import kz.akvelon.twitter.model.Tweet;
import kz.akvelon.twitter.repository.TagRepository;
import kz.akvelon.twitter.repository.TweetRepository;
import kz.akvelon.twitter.service.TweetService;
import kz.akvelon.twitter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {
    private final TweetRepository tweetRepository;

    private final TagRepository tagRepository;

    @Override
    @Transactional
    public Tweet save(Tweet tweet) {
        Account account = tweet.getAccount();
        account.getTweets().add(tweet);
        return tweetRepository.save(tweet);
    }

    @Override
    @Transactional
    public Tweet update(Tweet entity) {
        Tweet tweet = findById(entity.getId());
        tweet.setText(entity.getText());
        return tweet;
    }

    @Override
    @Transactional
    public void delete(Long tweetId) {
        Tweet tweet = findById(tweetId);
        Account account = findById(tweetId).getAccount();
        account.getTweets().remove(tweet);
        tweetRepository.delete(tweet);
    }

    @Override
    public Tweet findById(Long tweetId) {
        return tweetRepository.findById(tweetId)
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public TweetsDtoPage getTweets(Pageable pageable) {
        Page<Tweet> tweets = tweetRepository.findAll(pageable);

        List<TweetResponseDto> tweetsDto = tweets.stream()
                .map(TweetResponseDto::from)
                .collect(Collectors.toList());

        return TweetsDtoPage.builder()
                .tweets(tweetsDto)
                .size(10)
                .totalPages(10)
                .build();
    }

    @Override
    public List<Tweet> findTweetsByDescription(String text, Pageable pageable) {
        return tweetRepository.findTweetsByTextContaining(text, pageable);
    }

    @Override
    public void addTag(Tweet tweet, Tag tag) {
        Tweet tweetDB = findById(tweet.getId());

        if (!tweetDB.getTags().contains(tag)) {
            tweetDB.getTags().add(tag);
            Tag tagDB = tagRepository.findByTagName(tag.getTagName());
            tagDB.setTweetsCount(tagDB.getTweetsCount() + 1);
            tagDB.getTweets().add(tweetDB);
            tagRepository.save(tagDB);
        }

        update(tweetDB);
    }

}
