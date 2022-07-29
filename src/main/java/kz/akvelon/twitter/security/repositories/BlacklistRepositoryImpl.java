package kz.akvelon.twitter.security.repositories;

import kz.akvelon.twitter.security.config.JwtSecurityConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
@ConditionalOnBean(value = JwtSecurityConfig.class)
public class BlacklistRepositoryImpl implements BlacklistRepository {
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void save(String token) {
        redisTemplate.opsForValue().set(token, "");
    }

    @Override
    public boolean exists(String token) {
        Boolean hasToken = redisTemplate.hasKey(token);
        return hasToken != null && hasToken;
    }
}
