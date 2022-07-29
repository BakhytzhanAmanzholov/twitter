package kz.akvelon.twitter.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static kz.akvelon.twitter.config.MqConstants.*;


@Configuration
public class MqConfig {

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(SPRING_BOOT_EXCHANGE);
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        return container;
    }

    // Email Sender Queue

    @Bean
    Queue emailQueue() {
        return new Queue(QUEUE_NAME, false);
    }

    @Bean
    Binding emailBinding(@Qualifier("emailQueue") Queue emailQueue, TopicExchange exchange) {
        return BindingBuilder.bind(emailQueue).to(exchange).with(EMAIL_BINDING_NAME);
    }

    @Bean
    DirectExchange emailDeadLetterExchange() {
        return new DirectExchange(EMAIL_DLQ_EXCHANGE_NAME);
    }


    // Feed Tweet Queue

    @Bean
    Queue feedQueue() {
        return new Queue(FEED_QUEUE_NAME, false);
    }

    @Bean
    Binding feedBinding(@Qualifier("feedQueue") Queue feedQueue, TopicExchange exchange) {
        return BindingBuilder.bind(feedQueue).to(exchange).with(FEED_BINDING_NAME);
    }

    @Bean
    DirectExchange feedDeadLetterExchange() {
        return new DirectExchange(FEED_DLQ_EXCHANGE_NAME);
    }
}
