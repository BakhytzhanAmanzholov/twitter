package kz.akvelon.twitter.service.implementation;

import kz.akvelon.twitter.dto.response.tweets.TweetResponseDto;
import kz.akvelon.twitter.dto.response.tweets.TweetsDtoPage;
import kz.akvelon.twitter.model.*;
import kz.akvelon.twitter.repository.PollChoiceRepository;
import kz.akvelon.twitter.repository.PollRepository;
import kz.akvelon.twitter.repository.TagRepository;
import kz.akvelon.twitter.repository.TweetRepository;
import kz.akvelon.twitter.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {
    private final TweetRepository tweetRepository;

    private final ModerationService moderationService;

    private final TagRepository tagRepository;

    private final PollRepository pollRepository;

    private final PollChoiceRepository pollChoiceRepository;

    private final UserService userService;

    private final FeedService feedService;

    private final TagsService tagsService;
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
    public Tweet findById(Long tweetId) {
        String email = userService.isLogged();
        feedService.creatingTweetFeed(email, tweetId);

        return tweetRepository.findById(tweetId)
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public Tweet save(Tweet tweet) {
        Account account = userService.findByEmail(userService.isLogged());
        tweet.setAccount(account);
        account.getTweets().add(tweet);
        Tweet finalTweet = tweetRepository.save(tweet);
        moderationService.moderateTweet(finalTweet.getId()); // Send to RabbitMQ queue
        return finalTweet;
    }

    @Override
    public void delete(Long tweetId) {
        Tweet tweet = findById(tweetId);
        Account account = findById(tweetId).getAccount();
        account.getTweets().remove(tweet);
        tweetRepository.delete(tweet);
    }

    @Override
    public List<Tweet> findTweetsByDescription(String text, Pageable pageable) {
        return tweetRepository.findTweetsByTextContaining(text, pageable);
    }

    @Override
    public Tweet update(Tweet entity) {
        Tweet tweet = findById(entity.getId());
        tweet.setText(entity.getText());
        return tweet;
    }

    @Override
    public TweetResponseDto addTag(Long tweetId, String tagName) {
        Tweet tweet = findById(tweetId);
        Tag tag = tagsService.findByName(tagName);

        Tweet tweetDB = findById(tweet.getId());

        if (!tweetDB.getTags().contains(tag)) {
            tweetDB.getTags().add(tag);
            Tag tagDB = tagRepository.findByTagName(tag.getTagName());
            tagDB.setTweetsCount(tagDB.getTweetsCount() + 1);
            tagDB.getTweets().add(tweetDB);
            tagRepository.save(tagDB);
        } else {
            throw new IllegalArgumentException("Tag is already contains");
        }

        update(tweetDB);

        return TweetResponseDto.from(tweetDB);
    }

    @Override
    public Tweet createPoll(Long tweetId, Long pollDateTime, List<String> choices) {
        if (choices.size() < 2 || choices.size() > 4) {
            throw new IllegalArgumentException();
        }

        Tweet createdTweet = findById(tweetId);
        Poll poll = new Poll();
        poll.setTweet(createdTweet);
        poll.setDateTime(Date.valueOf(LocalDate.now()));

        List<PollChoice> pollChoices = new ArrayList<>();

        choices.forEach(choice -> {
            if (choice.length() == 0 || choice.length() > 25) {
                throw new IllegalArgumentException();
            }

            PollChoice pollChoice = new PollChoice();
            pollChoice.setChoice(choice);
            pollChoiceRepository.save(pollChoice);
            pollChoices.add(pollChoice);
        });

        poll.setPollChoices(pollChoices);
        poll = pollRepository.save(poll);
        createdTweet.setPoll(poll);
        return findById(createdTweet.getId());
    }

    @Override
    public Tweet voteInPoll(Account user, Long tweetId, Long pollId, Long pollChoiceId) {
        Poll poll = pollRepository.findById(pollId)
                .orElseThrow(IllegalArgumentException::new);
        PollChoice pollChoice = pollChoiceRepository.findById(pollChoiceId)
                .orElseThrow(IllegalArgumentException::new);
        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(IllegalArgumentException::new);

        if (!tweet.getPoll().getId().equals(poll.getId())) {
            throw new IllegalArgumentException();
        }


        List<PollChoice> pollChoices = tweet.getPoll().getPollChoices().stream()
                .peek(choice -> {
                    if (choice.getId().equals(pollChoice.getId())) {
                        choice.getVotedUser().add(user);
                    }
                })
                .collect(Collectors.toList());

        tweet.getPoll().setPollChoices(pollChoices);

        return findById(tweet.getId());
    }

    @Override
    @Transactional
    public String deleteScheduledTweets(List<Long> tweetsIds) {
        tweetsIds.forEach(this::delete);
        return "Deleted scheduled tweets";
    }

}
