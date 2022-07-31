package kz.akvelon.twitter.controller.config;
import kz.akvelon.twitter.model.Account;
import kz.akvelon.twitter.model.Role;
import kz.akvelon.twitter.security.details.UserDetailsImpl;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Collections;

@TestConfiguration
@EnableAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class,
        RedisAutoConfiguration.class
})

public class ConfigurationForControllersTest {
    public static final String MOCK_USERNAME = "yeldos.manap@gmail.com";

    @Bean
    @Primary
    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                if (username.equals(MOCK_USERNAME)) {
                    return new UserDetailsImpl(
                            Account.builder()
                                    .email(MOCK_USERNAME)
                                    .roles(Collections.singletonList(new Role("ROLE_USER")))
                                    .confirmed(true)
                                    .build());
                } else {
                    throw new UsernameNotFoundException("User <" + username + "> not found");
                }
            }
        };
    }

}
