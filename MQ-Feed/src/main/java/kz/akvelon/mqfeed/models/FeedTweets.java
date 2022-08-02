package kz.akvelon.mqfeed.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class FeedTweets {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private Account account;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<TweetPercentages> tweetList;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Tweet> tweets;

    @Override
    public String toString() {
        return "FeedTweets{" +
                "id=" + id +
                ", tweetList=" + tweetList +
                ", tweets=" + tweets +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FeedTweets that = (FeedTweets) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
