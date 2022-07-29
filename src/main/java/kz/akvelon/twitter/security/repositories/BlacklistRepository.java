package kz.akvelon.twitter.security.repositories;

public interface BlacklistRepository {
    void save(String token);

    boolean exists(String token);
}
