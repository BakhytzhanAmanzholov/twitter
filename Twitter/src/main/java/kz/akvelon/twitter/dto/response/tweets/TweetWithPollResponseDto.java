package kz.akvelon.twitter.dto.response.tweets;

import kz.akvelon.twitter.dto.response.ReactionDto;
import kz.akvelon.twitter.dto.response.users.UserInfoDto;
import kz.akvelon.twitter.model.Poll;
import kz.akvelon.twitter.model.ReactionInfo;
import kz.akvelon.twitter.model.Tweet;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Data
@Slf4j
@Builder
public class TweetWithPollResponseDto {
    private Long id;

    private String text;

    private List<ReactionDto> reactions;

    private String date;

    private String scheduledDate;

    private UserInfoDto user;

    private PollDto poll;


    public static TweetWithPollResponseDto from(Tweet tweet) {
        TweetWithPollResponseDto tweetWithPollResponseDto = TweetWithPollResponseDto.builder()
                .id(tweet.getId())
                .text(tweet.getText())
                .date(tweet.getDate().toString())
                .user(UserInfoDto.from(tweet.getAccount()))
                .poll(PollDto.from(tweet.getPoll()))
                .reactions(new ArrayList<>())
                .build();

        if (tweet.getReactions() != null) {
            for (ReactionInfo reaction : tweet.getReactions().values()) {
                tweetWithPollResponseDto.getReactions().add(ReactionDto.from(
                        reaction
                ));
            }
        }

        if (tweet.getScheduledDate() != null) {
            tweetWithPollResponseDto.setScheduledDate(tweet.getScheduledDate().toString());
        }

//        System.out.println(tweetWithPollResponseDto);
        return tweetWithPollResponseDto;
    }
}
