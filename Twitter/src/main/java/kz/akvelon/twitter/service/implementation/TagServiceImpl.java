package kz.akvelon.twitter.service.implementation;

import kz.akvelon.twitter.dto.response.tweets.TweetResponseDto;
import kz.akvelon.twitter.model.Tag;
import kz.akvelon.twitter.repository.TagRepository;
import kz.akvelon.twitter.repository.TweetRepository;
import kz.akvelon.twitter.service.TagsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagsService {

    private final TagRepository tagRepository;
    private final TweetRepository tweetRepository;

    @Override
    public Tag addTag(String name) {
        if (tagRepository.existsByTagName(name)) {
            throw new IllegalArgumentException("Tag is already exists");
        }

        Tag tag = Tag.builder()
                .tagName(name)
                .tweetsCount(0L)
                .tweets(new HashSet<>())
                .build();

        return tagRepository.save(tag);
    }

    @Override
    public List<Tag> getTags() {
        return tagRepository.findByOrderByTweetsCountDesc();
    }

    @Override
    public Tag findByName(String tagName) {
        Tag tag = tagRepository.findByTagName(tagName);

        if(tag == null) {
            throw new IllegalArgumentException("No tag with this name found");
        }

        return tagRepository.findByTagName(tagName);
    }

    @Override
    public List<TweetResponseDto> getTweetsByTagName(String tagName) {
        return tweetRepository.getTweetsByTagName(tagName).stream()
                .map(TweetResponseDto::from)
                .collect(Collectors.toList());
    }
}
