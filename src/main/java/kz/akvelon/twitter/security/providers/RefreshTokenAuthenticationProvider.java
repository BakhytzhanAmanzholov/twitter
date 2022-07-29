package kz.akvelon.twitter.security.providers;

import com.auth0.jwt.exceptions.JWTVerificationException;
import kz.akvelon.twitter.security.authentication.RefreshTokenAuthentication;
import kz.akvelon.twitter.security.config.JwtSecurityConfig;
import kz.akvelon.twitter.security.exceptions.RefreshTokenException;
import kz.akvelon.twitter.security.utils.JwtUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Component
@ConditionalOnBean(value = JwtSecurityConfig.class)
@Slf4j
public class RefreshTokenAuthenticationProvider implements AuthenticationProvider {

    JwtUtil jwtUtil;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = (String)authentication.getCredentials();
        try {
            return jwtUtil.buildAuthentication(token);
        } catch (JWTVerificationException e) {
            log.info(e.getMessage());
            throw new RefreshTokenException(e.getMessage(), e);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return RefreshTokenAuthentication.class.isAssignableFrom(authentication);
    }
}

