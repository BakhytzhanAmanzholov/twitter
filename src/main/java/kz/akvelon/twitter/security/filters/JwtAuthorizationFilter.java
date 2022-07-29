package kz.akvelon.twitter.security.filters;

import com.auth0.jwt.exceptions.JWTVerificationException;
import kz.akvelon.twitter.security.config.JwtSecurityConfig;
import kz.akvelon.twitter.security.utils.AuthorizationHeaderUtil;
import kz.akvelon.twitter.security.utils.JwtUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static kz.akvelon.twitter.constants.GlobalApplicationConstants.AUTHENTICATION_URL;


@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Component
@Slf4j
@ConditionalOnBean(value = JwtSecurityConfig.class)
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    JwtUtil jwtUtil;

    AuthorizationHeaderUtil authorizationHeaderUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().equals(AUTHENTICATION_URL)) {
            filterChain.doFilter(request, response);
        } else {
            if (authorizationHeaderUtil.hasAuthorizationToken(request)) {
                String jwt = authorizationHeaderUtil.getToken(request);
                try {
                    Authentication authenticationToken = jwtUtil.buildAuthentication(jwt);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request, response);
                } catch (JWTVerificationException e) {
                    log.info(e.getMessage());
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                }
            } else {
                filterChain.doFilter(request, response);
            }
        }
    }

}

