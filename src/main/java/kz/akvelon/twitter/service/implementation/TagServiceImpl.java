package kz.akvelon.twitter.service.implementation;

import kz.akvelon.twitter.dto.response.tweets.TweetResponseDto;
import kz.akvelon.twitter.model.Tag;
import kz.akvelon.twitter.model.Tweet;
import kz.akvelon.twitter.repository.TagRepository;
import kz.akvelon.twitter.repository.TweetRepository;
import kz.akvelon.twitter.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final TweetRepository tweetRepository;

    @Override
    public List<Tag> getTags() {
        return tagRepository.findByOrderByTweetsCountDesc();
    }

    @Override
    public Tag findByName(String tagName) {
        return tagRepository.findByTagName(tagName);
    }

    @Override
    public List<TweetResponseDto> getTweetsByTagName(String tagName) {
        return tweetRepository.getTweetsByTagName(tagName).stream()
                .map(TweetResponseDto::from)
                .collect(Collectors.toList());
    }
}
