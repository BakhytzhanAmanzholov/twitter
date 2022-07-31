package kz.akvelon.mqmoderation.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

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
}
