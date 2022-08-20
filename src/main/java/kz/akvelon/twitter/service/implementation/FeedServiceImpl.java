package kz.akvelon.twitter.service.implementation;

import kz.akvelon.twitter.config.mq.MqConstants;
import kz.akvelon.twitter.model.Account;
import kz.akvelon.twitter.model.FeedTweets;
import kz.akvelon.twitter.repository.FeedRepository;
import kz.akvelon.twitter.service.FeedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FeedServiceImpl implements FeedService {

    private final RabbitTemplate rabbitTemplate;

    private final FeedRepository feedRepository;

    @Override
    public void creatingTweetFeed(String email, Long tweetId) {
        String message = email + "." + tweetId;

        rabbitTemplate.convertAndSend(MqConstants.SPRING_BOOT_EXCHANGE, "feed.bar.baz",
                new Message(message.getBytes()));
    }

    @Override
    public FeedTweets findByAccount(Account account) {
        return feedRepository.findByAccount(account);
    }

    @Override
    public FeedTweets save(FeedTweets feedTweets) {
        return feedRepository.save(feedTweets);
    }
}
