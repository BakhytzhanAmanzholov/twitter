package kz.akvelon.twitter.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Builder
@Data
@Table(name = "tags")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tags_sequence")
    @SequenceGenerator(name = "tags_sequence", sequenceName = "tags_sequence", initialValue = 1, allocationSize = 1)
    private Long id;

    @Column(name = "tag_name")
    private String tagName;

    @Column(name = "tweets_count")
    private Long tweetsCount;

    @ManyToMany
    @JoinTable(name = "tweets_tags",
            joinColumns = @JoinColumn(name = "tags_id"),
            inverseJoinColumns = @JoinColumn(name = "tweets_id"))
    private List<Tweet> tweets;
}
