package kz.akvelon.twitter.model;

import kz.akvelon.twitter.dto.request.RegistrationDto;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String email;
    private String username;
    private String password;

    private Boolean confirmed = false;

    private Boolean banned = false;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles = new ArrayList<>();

    @OneToMany
    @ToString.Exclude
    private List<Tweet> tweets = new ArrayList<>();

    @OneToMany
    @ToString.Exclude
    private List<Tweet> retweets = new ArrayList<>();

    @OneToMany
    @ToString.Exclude
    private List<Tweet> quoteTweets = new ArrayList<>();

    @ManyToMany
    @ToString.Exclude
    private Map<Reaction, ReactionInfo> reactions = new HashMap<>();

    @ManyToMany
    @ToString.Exclude
    private List<Account> listSubscribers = new ArrayList<>();

    @ManyToMany
    @ToString.Exclude
    private List<Account> listSubscriptions = new ArrayList<>();

    private Integer subscribers = 0;
    private Integer subscriptions = 0;

    public Account(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.roles = new ArrayList<>();
    }

    public Account(Long id, String email, String username) {
        this.id = id;
        this.email = email;
        this.username = username;
    }

    public static Account fromRequestDto(RegistrationDto registrationDto){
        return Account.builder()
                .email(registrationDto.getEmail())
                .username(registrationDto.getUsername())
                .password(registrationDto.getPassword())
                .roles(new ArrayList<>())
                .banned(true)
                .confirmed(false)
                .subscribers(0)
                .subscriptions(0)
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Account account = (Account) o;
        return id != null && Objects.equals(id, account.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
