package kz.akvelon.twitter.repository;

import kz.akvelon.twitter.model.Account;
import kz.akvelon.twitter.model.Tweet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long> {
    List<Tweet> findByAccount_Id(Long accountId);

    Optional<Tweet> findTweetByIdAndAccount_Id(Long id, Long accountId);

    List<Tweet> findTweetsByTextContaining(String text, Pageable pageable);

    @Query("SELECT tagTweet as tweet " +
            "FROM Tag tag " +
            "LEFT JOIN tag.tweets tagTweet " +
            "WHERE tag.tagName = :tagName " +
            "ORDER BY tagTweet.date DESC")
    List<Tweet> getTweetsByTagName(String tagName);


}
