package kz.akvelon.twitter.dto.response.tweets;

import kz.akvelon.twitter.dto.response.ReactionDto;
import kz.akvelon.twitter.dto.response.users.UserInfoDto;
import kz.akvelon.twitter.model.ReactionInfo;
import kz.akvelon.twitter.model.Tweet;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class TweetResponseDto {
    private Long id;

    private String text;

    private List<ReactionDto> reactions;

    private LocalDateTime dateTime;

    private UserInfoDto user;


    public static TweetResponseDto from(Tweet tweet) {
        TweetResponseDto tweetResponseDto = TweetResponseDto.builder()
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
                .reactions(new ArrayList<>())
                .build();
        if(tweet.getReactions() != null){
            for (ReactionInfo reaction: tweet.getReactions().values()){
                tweetResponseDto.getReactions().add(ReactionDto.from(
                        reaction
                ));
            }
        }
        return tweetResponseDto;
    }
}
