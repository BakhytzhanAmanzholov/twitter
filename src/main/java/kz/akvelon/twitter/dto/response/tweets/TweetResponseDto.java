package kz.akvelon.twitter.dto.response.tweets;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(name = "Tweet response body")
public class TweetResponseDto {
    @Schema(name = "Tweet id", example = "1")
    private Long id;
    @Schema(name = "Tweet's text", example = "Lorem ipsum")
    private String text;
    @Schema(name = "Tweet's list of reactions")
    private List<ReactionDto> reactions;
    @Schema(name = "Tweet date")
    private String date;
    @Schema(name = "User of a tweet")
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
