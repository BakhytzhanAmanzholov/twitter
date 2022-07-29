package kz.akvelon.twitter.dto.response.tweets;

import kz.akvelon.twitter.dto.response.ReactionDto;
import kz.akvelon.twitter.dto.response.users.UserInfoDto;
import kz.akvelon.twitter.model.ReactionInfo;
import kz.akvelon.twitter.model.Tweet;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Slf4j
@Builder
public class TweetResponseDto {
    private Long id;

    private String text;

    private List<ReactionDto> reactions;

    private String date;

    private UserInfoDto user;


    public static TweetResponseDto from(Tweet tweet) {
        TweetResponseDto tweetResponseDto = TweetResponseDto.builder()
                .id(tweet.getId())
                .text(tweet.getText())
                .date(tweet.getDate().toString())
                .user(
                        UserInfoDto.from(tweet.getAccount())
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
