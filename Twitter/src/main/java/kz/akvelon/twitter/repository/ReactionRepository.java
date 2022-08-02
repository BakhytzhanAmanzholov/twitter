package kz.akvelon.twitter.repository;

import kz.akvelon.twitter.model.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReactionRepository extends JpaRepository<Reaction, Long> {
}
