package kz.akvelon.mqfeed.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

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

    @OneToOne
    private FeedTweets feedTweets;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id) && Objects.equals(email, account.email) && Objects.equals(username, account.username) && Objects.equals(password, account.password) && Objects.equals(confirmed, account.confirmed) && Objects.equals(banned, account.banned) && Objects.equals(subscribers, account.subscribers) && Objects.equals(subscriptions, account.subscriptions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, username, password, confirmed, banned, subscribers, subscriptions);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", confirmed=" + confirmed +
                ", banned=" + banned +
                '}';
    }
}