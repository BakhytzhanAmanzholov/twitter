package kz.akvelon.twitter.dto.response.users;

import kz.akvelon.twitter.dto.response.tweets.QuoteTweetResponseDto;
import kz.akvelon.twitter.dto.response.tweets.TweetResponseDto;
import kz.akvelon.twitter.model.Account;
import kz.akvelon.twitter.model.Tweet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String email;
    private String username;
    private Integer subscribers;
    private Integer subscriptions;

    private List<UserInfoDto> listSubscribers = new ArrayList<>();
    private List<UserInfoDto> listSubscriptions = new ArrayList<>();

    private List<TweetResponseDto> tweets;
    private List<TweetResponseDto> retweets;
    private List<QuoteTweetResponseDto> quotesTweets;

    public static UserDto from(Account entity) {
        UserDto account = UserDto.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .username(entity.getUsername())
                .subscribers(entity.getSubscribers())
                .subscriptions(entity.getSubscriptions())
                .tweets(new ArrayList<>())
                .retweets(new ArrayList<>())
                .quotesTweets(new ArrayList<>())
                .listSubscriptions(new ArrayList<>())
                .listSubscribers(new ArrayList<>())
                .build();

        for (Tweet tweet : entity.getTweets()) {
            account.getTweets().add(TweetResponseDto.from(tweet));
        }

        for (Tweet tweet : entity.getRetweets()) {
            account.getRetweets().add(TweetResponseDto.from(tweet));
        }

        for (Tweet tweet : entity.getQuoteTweets()) {
            account.getQuotesTweets().add(QuoteTweetResponseDto.from(tweet));
        }

        for(Account subscription: entity.getListSubscriptions()){
            account.getListSubscriptions().add(UserInfoDto.from(subscription));
        }

        for(Account subscriber: entity.getListSubscribers()){
            account.getListSubscribers().add(UserInfoDto.from(subscriber));
        }

        return account;
    }
}
