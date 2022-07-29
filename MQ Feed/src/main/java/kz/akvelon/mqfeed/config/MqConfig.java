package kz.akvelon.mqfeed.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MqConfig {

    @Bean
    Queue queue() {
        Queue queue = QueueBuilder.nonDurable()
                .withArgument("x-dead-feed-exchange", MqConstants.DLQ_EXCHANGE_NAME)
                .withArgument("x-dead-feed-routing-key", MqConstants.DLQ_ROUTING_KEY)
                .autoDelete()
                .build();
        queue.setActualName(MqConstants.QUEUE_NAME);
        return queue;
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(MqConstants.SPRING_BOOT_EXCHANGE);
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("feed.bar.#");
    }

    @Bean
    RabbitListenerContainerFactory<SimpleMessageListenerContainer>
    containerFactory(ConnectionFactory rabbitConnectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(rabbitConnectionFactory);
        factory.setPrefetchCount(3);
        factory.setConcurrentConsumers(1);
        factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        return factory;
    }
}
