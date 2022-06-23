package kz.akvelon.twitter.repository;

import kz.akvelon.twitter.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Account, Long> {
    Account findByEmail(String email);
}
