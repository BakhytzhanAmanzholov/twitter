package kz.akvelon.twitter.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReactionInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    private Reaction reaction;

    @ManyToOne
    private Account account;

    @ManyToOne
    private Tweet tweet;

    private int count = 0;

    public ReactionInfo(Reaction reaction, int count) {
        this.reaction = reaction;
        this.count = count;
    }
}
