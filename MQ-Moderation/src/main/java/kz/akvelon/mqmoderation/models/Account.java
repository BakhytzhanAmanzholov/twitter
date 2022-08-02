package kz.akvelon.mqmoderation.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String email;
    private String username;
    private String password;

    private Boolean confirmed = false;

    private Boolean banned = false;

    private Integer subscribers = 0;
    private Integer subscriptions = 0;

    @OneToMany
    private List<Tweet> tweets = new ArrayList<>();
}
