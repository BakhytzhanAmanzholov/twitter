package kz.akvelon.mqmoderation.service;

import kz.akvelon.mqmoderation.models.Tweet;

public interface TweetService {
    Tweet findById(Long id);

    boolean moderate(Long id);

    Tweet update(Long id, String text);
}
