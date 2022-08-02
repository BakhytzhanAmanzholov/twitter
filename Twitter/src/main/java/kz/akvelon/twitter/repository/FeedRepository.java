package kz.akvelon.twitter.repository;

import kz.akvelon.twitter.model.Account;
import kz.akvelon.twitter.model.FeedTweets;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedRepository extends JpaRepository<FeedTweets, Long> {

    FeedTweets findByAccount(Account account);
}
