package kz.akvelon.twitter.model;

import kz.akvelon.twitter.dto.request.TweetRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Entity
@Builder
@Data
@AllArgsConstructor
@Table(name = "tweets")
public class Tweet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "text")
    private String text;

    @Column(name = "date_time")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToMany
    private Map<Reaction, ReactionInfo> reactions;

    @OneToOne
    private Tweet quoteTweet;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "tweets_tags",
            joinColumns = {@JoinColumn(name = "tweet_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")})
    @ToString.Exclude
    Set<Tag> tags = new HashSet<>();

    @Column(name = "scheduled_date")
    private LocalDateTime scheduledDate;


    @OneToOne
    @JoinTable(name = "tweet_pool",
            joinColumns = @JoinColumn(name = "tweets_id"),
            inverseJoinColumns = @JoinColumn(name = "pools_id"))
    private Poll poll;


    public Tweet() {

    }

    public static Tweet fromRequestDto(TweetRequestDto tweetRequestDto){
        return Tweet.builder()
                .text(tweetRequestDto.getText())
                .date(Date.valueOf(LocalDate.now()))
                .build();
    }
}
