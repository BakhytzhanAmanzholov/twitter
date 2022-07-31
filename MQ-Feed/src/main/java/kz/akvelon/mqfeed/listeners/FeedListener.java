package kz.akvelon.mqfeed.listeners;

import kz.akvelon.mqfeed.models.Account;
import kz.akvelon.mqfeed.models.FeedTweets;
import kz.akvelon.mqfeed.models.Tweet;
import kz.akvelon.mqfeed.service.AccountService;
import kz.akvelon.mqfeed.service.FeedPercentagesService;
import kz.akvelon.mqfeed.service.TweetService;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class FeedListener {

    private final AccountService accountService;

    private final TweetService tweetService;

    private final FeedPercentagesService feedService;

    @RabbitListener(queues = "feed_queue", containerFactory = "containerFactory")
    public void onMessage(Message message) {
        try {
            String[] mes = new String(message.getBody()).split("\\.");
            String email = mes[0] + '.' + mes[1];
            Long id = Long.valueOf(mes[2]);
            log.info(email + " " + id);
            Account account = accountService.findByEmail(email);
            Tweet tweet = tweetService.findById(id);

            FeedTweets feed = feedService.findById(accountService.isExistFeed(account).getId());

            feedService.addTweetsToFeed(feed, tweet);
            feedService.sorting(feed);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AmqpRejectAndDontRequeueException(e);
        }
    }

}
