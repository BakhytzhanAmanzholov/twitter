package kz.akvelon.twitter.integrations.base;

import com.auth0.jwt.exceptions.JWTVerificationException;
import kz.akvelon.twitter.model.Account;
import kz.akvelon.twitter.model.Role;
import kz.akvelon.twitter.security.details.UserDetailsImpl;
import kz.akvelon.twitter.security.repositories.BlacklistRepository;
import kz.akvelon.twitter.security.utils.JwtUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import redis.embedded.RedisServer;

import java.util.Collections;

import static kz.akvelon.twitter.controller.config.ConfigurationForControllersTest.MOCK_USERNAME;
import static org.mockito.Mockito.when;

public class WithSecurity extends WithDatabase {
    public static final String BEARER = "Bearer ";

    public static final String VALID_TOKEN = "token1";

    public static final String INVALID_TOKEN = "token2";

    public static final String REVOKED_TOKEN = "token3";

    @Autowired
    private BlacklistRepository blacklistRepository;

    @MockBean
    private static JwtUtil jwtUtil;

    private static RedisServer redisServer;

    @BeforeEach()
    public void setUp() {
        revokeToken();
        mockJwtUtil(jwtUtil);
    }

    @BeforeAll
    public static void setUpRedisServer() {
        redisServer = new RedisServer();
        redisServer.start();
    }

    @AfterAll
    public static void tearDownRedisServer() {
        redisServer.stop();
    }

    private void revokeToken() {
        blacklistRepository.save(REVOKED_TOKEN);
    }

    private void mockJwtUtil(JwtUtil jwtUtil) {

        when(jwtUtil.buildAuthentication(VALID_TOKEN))
                .thenReturn(new UsernamePasswordAuthenticationToken(
                        new UserDetailsImpl(
                                Account.builder()
                                        .email(MOCK_USERNAME)
                                        .roles(Collections.singletonList(new Role("ROLE_USER")))
                                        .confirmed(true)
                                        .build()
                        ), null,
                        Collections.singleton(new SimpleGrantedAuthority("USER"))
                ));

        when(jwtUtil.buildAuthentication(INVALID_TOKEN))
                .thenThrow(JWTVerificationException.class);
    }

}
