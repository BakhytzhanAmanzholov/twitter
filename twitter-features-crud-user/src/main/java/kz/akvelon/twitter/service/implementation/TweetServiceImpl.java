package kz.akvelon.twitter.service.implementation;

import kz.akvelon.twitter.dto.TweetResponseDto;
import kz.akvelon.twitter.dto.TweetsDtoPage;
import kz.akvelon.twitter.model.Tweet;
import kz.akvelon.twitter.repository.TweetRepository;
import kz.akvelon.twitter.service.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {
    private final TweetRepository tweetRepository;

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
    public Tweet getTweetById(Long tweetId) {
        return tweetRepository.findById(tweetId)
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public Tweet createTweet(Tweet tweet) {
        return tweetRepository.save(tweet);
    }

    @Override
    public Tweet deleteTweet(Long accountId, Long tweetId) {
        Optional<Tweet> tweetOptional = tweetRepository.findTweetByIdAndAccount_Id(tweetId, accountId);

        if (tweetOptional.isPresent()) {
            tweetRepository.delete(tweetOptional.get());
            return tweetOptional.get();
        } else {
            throw new IllegalArgumentException("Account does not have tweet with id " + tweetId);
        }
    }

    @Override
    public List<Tweet> findTweetsByDescription(String text, Pageable pageable) {
        return tweetRepository.findTweetsByTextContaining(text, pageable);
    }
}
