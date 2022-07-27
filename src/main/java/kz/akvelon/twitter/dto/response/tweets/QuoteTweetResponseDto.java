package kz.akvelon.twitter.dto.response.tweets;

import io.swagger.v3.oas.annotations.media.Schema;
import kz.akvelon.twitter.dto.response.ReactionDto;
import kz.akvelon.twitter.model.ReactionInfo;
import kz.akvelon.twitter.model.Tweet;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
@Schema(name = "Quoted tweet response")
public class QuoteTweetResponseDto {
    @Schema(name = "Tweet id", example = "1")
    private Long id;
    @Schema(name = "Tweet's text", example = "Lorem ipsum")
    private String text;
    @Schema(name = "Tweet's list of reactions")
    private List<ReactionDto> reactions;
    @Schema(name = "Tweet date")
    private String date;
    @Schema(name = "Tweet quotes")
    private TweetResponseDto quotesTweet;

    public static QuoteTweetResponseDto from(Tweet tweet) {
        QuoteTweetResponseDto quoteTweet = QuoteTweetResponseDto.builder()
                .id(tweet.getId())
                .quotesTweet(
                        TweetResponseDto.from(tweet.getQuoteTweet())
                )
                .text(tweet.getText())
                .date(tweet.getDate().toString())
                .reactions(new ArrayList<>())
                .build();

        for (ReactionInfo reaction: tweet.getReactions().values()){
            quoteTweet.getReactions().add(ReactionDto.from(
                    reaction
            ));
        }
        return quoteTweet;
    }
}
