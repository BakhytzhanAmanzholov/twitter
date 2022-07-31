package kz.akvelon.twitter.repository;

import kz.akvelon.twitter.model.BlackList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlackListRepository extends JpaRepository<BlackList, Long> {
}
