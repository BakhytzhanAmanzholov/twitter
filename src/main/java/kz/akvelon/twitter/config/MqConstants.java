package kz.akvelon.twitter.config;

public class MqConstants {
    public static final String SPRING_BOOT_EXCHANGE = "spring-boot-exchange";

    public static final String QUEUE_NAME = "email";

    public final static String DLQ_EXCHANGE_NAME = "deadLetterExchange";

    public final static String EMAIL_BINDING_NAME = "foo.bar.#";

}
