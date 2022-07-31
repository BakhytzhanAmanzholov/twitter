package kz.akvelon.mqmoderation.config;

public class MqConstants {
    public static final String SPRING_BOOT_EXCHANGE = "spring-boot-exchange";

    public static final String QUEUE_NAME = "moderation_queue";

    public final static String DLQ_EXCHANGE_NAME = "deadModerationExchange";
    public final static String DLQ_ROUTING_KEY = "deadModeration";
}
