package kz.akvelon.twitter.dto.response.tweets;

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
public class QuoteTweetResponseDto {
    private Long id;
    private String text;
    private List<ReactionDto> reactions;
    private String date;
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
