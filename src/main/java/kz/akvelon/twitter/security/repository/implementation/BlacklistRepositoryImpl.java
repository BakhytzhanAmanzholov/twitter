package kz.akvelon.twitter.security.repository.implementation;

import kz.akvelon.twitter.security.repository.BlackListRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.data.redis.core.RedisTemplate;

@RequiredArgsConstructor
@Repository
@Slf4j
public class BlacklistRepositoryImpl implements BlackListRepository {

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
