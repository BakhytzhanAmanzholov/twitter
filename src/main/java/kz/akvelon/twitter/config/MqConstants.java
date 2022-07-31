package kz.akvelon.twitter.config;

public class MqConstants {
    public static final String SPRING_BOOT_EXCHANGE = "spring-boot-exchange";

    public static final String QUEUE_NAME = "email_queue";

    public final static String EMAIL_DLQ_EXCHANGE_NAME = "deadLetterExchange";

    public final static String EMAIL_BINDING_NAME = "foo.bar.#";


    public static final String FEED_QUEUE_NAME = "feed_queue";


    public final static String FEED_BINDING_NAME = "feed.bar.#";

    public final static String FEED_DLQ_EXCHANGE_NAME = "deadFeedExchange";


    public static final String MODERATION_QUEUE_NAME = "moderation_queue";


    public final static String MODERATION_BINDING_NAME = "moderation.bar.#";

    public final static String MODERATION_DLQ_EXCHANGE_NAME = "deadModerationExchange";

}
