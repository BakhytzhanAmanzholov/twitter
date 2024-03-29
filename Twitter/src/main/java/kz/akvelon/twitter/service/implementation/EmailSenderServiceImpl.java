package kz.akvelon.twitter.service.implementation;

import kz.akvelon.twitter.config.mq.MqConstants;
import kz.akvelon.twitter.service.EmailSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import org.springframework.amqp.core.Message;


import org.springframework.amqp.rabbit.core.RabbitTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailSenderServiceImpl implements EmailSenderService {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void sendEmail(String email, String username) {
        String mes = email + "." + username;
        rabbitTemplate.convertAndSend(MqConstants.SPRING_BOOT_EXCHANGE, "foo.bar.baz",
                new Message(mes.getBytes()));
    }

}
