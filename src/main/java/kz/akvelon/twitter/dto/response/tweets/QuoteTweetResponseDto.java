package kz.akvelon.twitter.dto.response.tweets;

import kz.akvelon.twitter.model.Tweet;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class QuoteTweetResponseDto {
    private Long id;
    private String text;
    private LocalDateTime dateTime;
    private TweetResponseDto quotesTweet;

    public static QuoteTweetResponseDto from(Tweet tweet){
        return QuoteTweetResponseDto.builder()
                .id(tweet.getId())
                .quotesTweet(
                        TweetResponseDto.from(tweet.getQuoteTweet())
                )
                .text(tweet.getText())
                .dateTime(tweet.getDateTime())
                .build();
    }
}
