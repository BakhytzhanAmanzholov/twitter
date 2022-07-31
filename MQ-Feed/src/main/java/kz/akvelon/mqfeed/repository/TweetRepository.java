package kz.akvelon.mqfeed.repository;

import kz.akvelon.mqfeed.models.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TweetRepository extends JpaRepository<Tweet, Long> {
    Optional<Tweet> findById(Long id);

    List<Tweet> findByTextLike(String text);
}
