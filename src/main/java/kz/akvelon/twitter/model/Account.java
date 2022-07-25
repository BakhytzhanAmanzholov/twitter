package kz.akvelon.twitter.model;

import kz.akvelon.twitter.dto.request.RegistrationDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
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
    private List<Tweet> tweets = new ArrayList<>();

    @OneToMany
    private List<Tweet> retweets = new ArrayList<>();

    @OneToMany
    private List<Tweet> quoteTweets = new ArrayList<>();

    @ManyToMany
    private Map<Reaction, ReactionInfo> reactions = new HashMap<>();

    @ManyToMany
    private List<Account> listSubscribers = new ArrayList<>();

    @ManyToMany
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
}
