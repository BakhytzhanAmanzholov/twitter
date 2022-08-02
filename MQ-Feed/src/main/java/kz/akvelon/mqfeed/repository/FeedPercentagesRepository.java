package kz.akvelon.mqfeed.repository;

import kz.akvelon.mqfeed.models.FeedTweets;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedPercentagesRepository extends JpaRepository<FeedTweets, Long> {
}
