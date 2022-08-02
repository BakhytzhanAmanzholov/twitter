package kz.akvelon.twitter.repository;

import kz.akvelon.twitter.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
