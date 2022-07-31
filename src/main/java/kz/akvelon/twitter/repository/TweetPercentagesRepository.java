package kz.akvelon.twitter.repository;

import kz.akvelon.twitter.model.TweetPercentages;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TweetPercentagesRepository extends JpaRepository<TweetPercentages, Long> {
}
