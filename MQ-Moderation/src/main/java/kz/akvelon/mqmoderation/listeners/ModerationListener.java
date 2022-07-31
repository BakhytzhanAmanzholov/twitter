package kz.akvelon.mqmoderation.listeners;

import kz.akvelon.mqmoderation.models.Tweet;
import kz.akvelon.mqmoderation.service.TweetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ModerationListener {

    private final TweetService service;

    @RabbitListener(queues = "moderation_queue", containerFactory = "containerFactory")
    public void onMessage(Message message) {
        try {
            Long id = Long.valueOf(new String(message.getBody()));
            if(service.moderate(id)){
                log.info("Moderation failed by tweet id {}", id);
            }
        } catch (Exception e) {
            throw new AmqpRejectAndDontRequeueException(e);
        }
    }
}
