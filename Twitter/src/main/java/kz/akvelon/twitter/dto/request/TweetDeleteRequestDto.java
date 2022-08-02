package kz.akvelon.twitter.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class TweetDeleteRequestDto {
    private List<Long> tweetIds;
}
