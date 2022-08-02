package kz.akvelon.mqfeed.config;

public class MqConstants {
    public static final String SPRING_BOOT_EXCHANGE = "spring-boot-exchange";

    public static final String QUEUE_NAME = "feed_queue";

    public final static String DLQ_EXCHANGE_NAME = "deadFeedExchange";
    public final static String DLQ_ROUTING_KEY = "deadFeed";
}
