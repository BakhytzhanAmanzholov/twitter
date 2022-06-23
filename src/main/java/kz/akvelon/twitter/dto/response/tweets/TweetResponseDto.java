package kz.akvelon.twitter.dto.response.tweets;

import kz.akvelon.twitter.dto.response.users.UserInfoDto;
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

    private UserInfoDto user;

    public static TweetResponseDto from(Tweet tweet) {
        return TweetResponseDto.builder()
                .id(tweet.getId())
                .text(tweet.getText())
                .dateTime(tweet.getDateTime())
                .user(
                        UserInfoDto.builder()
                                .id(tweet.getAccount().getId())
                                .username(tweet.getAccount().getEmail())
                                .email(tweet.getAccount().getUsername())
                                .build()
                )
                .build();
    }
}
