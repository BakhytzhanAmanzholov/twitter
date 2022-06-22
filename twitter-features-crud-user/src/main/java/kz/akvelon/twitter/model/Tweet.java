package kz.akvelon.twitter.model;

import kz.akvelon.twitter.dto.TweetRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    public Tweet() {
        this.dateTime = LocalDateTime.now().withNano(0);
    }

    public static Tweet fromRequestDto(TweetRequestDto tweetRequestDto){
        return Tweet.builder()
                .text(tweetRequestDto.getText())
                .dateTime(LocalDateTime.now())
                .build();
    }
}
