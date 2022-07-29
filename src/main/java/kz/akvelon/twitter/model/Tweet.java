package kz.akvelon.twitter.model;

import kz.akvelon.twitter.dto.request.TweetRequestDto;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Entity
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@Table(name = "tweets")
public class Tweet implements Serializable {
    private static final long serialVersionUID = 1L;

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
    @ToString.Exclude
    private Map<Reaction, ReactionInfo> reactions;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "tweets_tags",
            joinColumns = {@JoinColumn(name = "tweet_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")})
    @ToString.Exclude
    Set<Tag> tags = new HashSet<>();

    @OneToOne
    private Tweet quoteTweet;

    public Tweet() {

    }

    public static Tweet fromRequestDto(TweetRequestDto tweetRequestDto) {
        return Tweet.builder()
                .text(tweetRequestDto.getText())
                .date(Date.valueOf(LocalDate.now()))
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Tweet tweet = (Tweet) o;
        return id != null && Objects.equals(id, tweet.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
