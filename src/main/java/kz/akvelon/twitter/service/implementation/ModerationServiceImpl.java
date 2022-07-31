package kz.akvelon.twitter.service.implementation;

import kz.akvelon.twitter.config.MqConstants;
import kz.akvelon.twitter.service.ModerationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ModerationServiceImpl implements ModerationService {
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void moderateTweet(Long tweetID) {
        String mes = tweetID.toString();

        rabbitTemplate.convertAndSend(MqConstants.SPRING_BOOT_EXCHANGE, "moderation.bar.baz",
                new Message(mes.getBytes()));
    }
}
