package kz.akvelon.mqmoderation.repositories;

import kz.akvelon.mqmoderation.models.BlackList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlackListRepository extends JpaRepository<BlackList, Long> {
}
