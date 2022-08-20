package kz.akvelon.twitter.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode
@Table(name = "pool_choices")
public class PollChoice {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pool_choices_seq")
    @SequenceGenerator(name = "pool_choices_seq", sequenceName = "pool_choices_seq", initialValue = 100, allocationSize = 1)
    private Long id;

    @Column(name = "choice")
    private String choice;

    @ManyToMany
    private List<Account> votedUser;

    public PollChoice() {
        this.votedUser = new ArrayList<>();
    }
}
