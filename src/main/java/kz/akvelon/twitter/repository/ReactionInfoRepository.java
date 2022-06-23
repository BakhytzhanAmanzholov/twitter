package kz.akvelon.twitter.repository;

import kz.akvelon.twitter.model.ReactionInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReactionInfoRepository extends JpaRepository<ReactionInfo, Long> {
}
