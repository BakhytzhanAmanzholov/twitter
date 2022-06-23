package kz.akvelon.twitter.model;

import kz.akvelon.twitter.dto.request.TweetRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
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
    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToMany
    private Map<Reaction, ReactionInfo> reactions;

    @OneToOne
    private Tweet quoteTweet;


    public Tweet() {
        this.dateTime = LocalDateTime.now().withNano(0);
    }

    public static Tweet fromRequestDto(TweetRequestDto tweetRequestDto){
        return Tweet.builder()
                .text(tweetRequestDto.getText())
                .dateTime(LocalDateTime.now()) // TODO: Исправить Schedule time и dateTime, чтобы показало нормально, а не
                                                // 2022-06-23T15:46:02.420474, для этого использовать Класс Time
                .build();
    }
}
