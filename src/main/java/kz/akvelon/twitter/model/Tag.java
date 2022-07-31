package kz.akvelon.twitter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tags")
public class Tag implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tags_sequence")
    @SequenceGenerator(name = "tags_sequence", sequenceName = "tags_sequence", initialValue = 1, allocationSize = 1)
    private Long id;

    @Column(name = "tag_name")
    private String tagName;

    @Column(name = "tweets_count")
    private Long tweetsCount;


    @ManyToMany(mappedBy = "tags", cascade = { CascadeType.ALL })
    @ToString.Exclude
    @JsonIgnore
    private Set<Tweet> tweets = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Tag tag = (Tag) o;
        return id != null && Objects.equals(id, tag.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
