package kz.akvelon.twitter.service;

import kz.akvelon.twitter.dto.response.tweets.TweetResponseDto;
import kz.akvelon.twitter.model.Tag;

import java.util.List;

public interface TagService {
    List<Tag> getTags();

    Tag findByName(String tagName);

    List<TweetResponseDto> getTweetsByTagName(String tagName);
}
