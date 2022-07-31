package kz.akvelon.mqemail.listeners;

import kz.akvelon.mqemail.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailListener {

    private final EmailService emailService;

    @RabbitListener(queues = "email_queue", containerFactory = "containerFactory")
    public void onMessage(Message message) {
        try {
            String[] mes = new String(message.getBody()).split("\\.");
            String email = mes[0] + '.' + mes[1];
            String username = mes[2];
            emailService.sendEmail(email, username);
        } catch (Exception e) {
            throw new AmqpRejectAndDontRequeueException(e);
        }
    }

}
