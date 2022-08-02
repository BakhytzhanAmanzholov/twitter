package kz.akvelon.mqmoderation.repositories;

import kz.akvelon.mqmoderation.models.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TweetRepository extends JpaRepository<Tweet, Long> {
}
