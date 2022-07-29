package kz.akvelon.twitter.security.utils.implementation;

import kz.akvelon.twitter.security.config.JwtSecurityConfig;
import kz.akvelon.twitter.security.utils.AuthorizationHeaderUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
@ConditionalOnBean(value = JwtSecurityConfig.class)
public class JwtRequestImpl implements AuthorizationHeaderUtil {

    private static final String AUTHORIZATION_HEADER_NAME = "AUTHORIZATION";

    private static final String BEARER = "Bearer ";

    @Override
    public boolean hasAuthorizationToken(HttpServletRequest request) {
        String header = request.getHeader(AUTHORIZATION_HEADER_NAME);
        return header != null && header.startsWith(BEARER);
    }

    @Override
    public String getToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER_NAME);
        return authorizationHeader.substring(BEARER.length());
    }
}

