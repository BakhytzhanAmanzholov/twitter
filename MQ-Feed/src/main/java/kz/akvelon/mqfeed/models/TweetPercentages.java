package kz.akvelon.mqfeed.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TweetPercentages implements Comparable<TweetPercentages>{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Tweet tweet;

    private Double percent;


    @Override
    public int compareTo(TweetPercentages o) {
        return (int) (o.getPercent() - this.percent);
    }
}
