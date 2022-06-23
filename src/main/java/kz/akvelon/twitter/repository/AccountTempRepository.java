package kz.akvelon.twitter.repository;

import kz.akvelon.twitter.model.AccountTemp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountTempRepository extends JpaRepository<AccountTemp, Long> {
    AccountTemp findByEmail(String email);
}
