package kz.akvelon.mqfeed.repository;

import kz.akvelon.mqfeed.models.TweetPercentages;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TweetPercentagesRepository extends JpaRepository<TweetPercentages, Long> {
}
