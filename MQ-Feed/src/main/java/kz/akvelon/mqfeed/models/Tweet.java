package kz.akvelon.mqfeed.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;

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


    public Tweet() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tweet tweet = (Tweet) o;
        return Objects.equals(id, tweet.id) && Objects.equals(text, tweet.text) && Objects.equals(date, tweet.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, date);
    }
}
