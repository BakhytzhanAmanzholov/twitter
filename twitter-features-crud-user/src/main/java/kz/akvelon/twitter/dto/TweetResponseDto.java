package kz.akvelon.twitter.dto;

import kz.akvelon.twitter.model.Tweet;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TweetResponseDto {
    private Long id;

    private String text;

    private LocalDateTime dateTime;

    private LocalDateTime scheduledDate;

    public static TweetResponseDto from(Tweet tweet) {
        return TweetResponseDto.builder()
                .id(tweet.getId())
                .text(tweet.getText())
                .dateTime(tweet.getDateTime())
                .build();
    }
}
