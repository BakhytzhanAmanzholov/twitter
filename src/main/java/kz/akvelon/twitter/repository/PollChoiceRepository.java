package kz.akvelon.twitter.repository;

import kz.akvelon.twitter.model.PollChoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PollChoiceRepository extends JpaRepository<PollChoice, Long> {
}
