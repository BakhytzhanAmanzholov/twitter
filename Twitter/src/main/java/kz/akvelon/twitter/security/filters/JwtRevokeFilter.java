package kz.akvelon.twitter.security.filters;


import kz.akvelon.twitter.security.config.JwtSecurityConfig;
import kz.akvelon.twitter.security.repository.BlackListRepository;
import kz.akvelon.twitter.security.utils.AuthorizationHeaderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static kz.akvelon.twitter.constants.GlobalApplicationConstants.REVOKE_TOKEN_URL;


@RequiredArgsConstructor
@Component
@ConditionalOnBean(value = JwtSecurityConfig.class)
public class JwtRevokeFilter extends OncePerRequestFilter {

    private final BlackListRepository blacklistRepository;

    private final AuthorizationHeaderUtil authorizationHeaderUtil;

    private final AntPathRequestMatcher revokeMatcher =
            new AntPathRequestMatcher(REVOKE_TOKEN_URL, "POST");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (revokeMatcher.matches(request)) {
            String token = authorizationHeaderUtil.getToken(request);
            blacklistRepository.save(token);
            response.setStatus(200);
        } else {
            filterChain.doFilter(request, response);
        }
    }
}

