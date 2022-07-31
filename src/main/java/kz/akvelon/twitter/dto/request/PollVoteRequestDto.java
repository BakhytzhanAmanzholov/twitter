package kz.akvelon.twitter.dto.request;

import lombok.Data;

@Data
public class PollVoteRequestDto {
    private String email;
    private Long tweetId;
    private Long pollId;
    private Long pollChoiceId;
}
