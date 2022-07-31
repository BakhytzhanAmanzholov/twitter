package kz.akvelon.twitter.repository;

import kz.akvelon.twitter.model.ReactionInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReactionInfoRepository extends JpaRepository<ReactionInfo, Long> {
}
