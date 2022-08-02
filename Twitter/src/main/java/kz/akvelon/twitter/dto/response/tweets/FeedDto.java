package kz.akvelon.twitter.dto.response.tweets;

import kz.akvelon.twitter.dto.response.ReactionDto;
import kz.akvelon.twitter.dto.response.users.UserInfoDto;
import kz.akvelon.twitter.model.FeedTweets;
import kz.akvelon.twitter.model.ReactionInfo;
import kz.akvelon.twitter.model.Tweet;
import kz.akvelon.twitter.model.TweetPercentages;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
public class FeedDto {
    private List<TweetResponseDto> tweets;

    public static FeedDto from(FeedTweets feed) {
        List<TweetResponseDto> tweetResponseDtos = new ArrayList<>();

        for(TweetPercentages tweet: feed.getTweetList()){
            tweetResponseDtos.add(TweetResponseDto.from(tweet.getTweet()));
        }
        return  FeedDto.builder()
                .tweets(tweetResponseDtos)
                .build();
    }
}
