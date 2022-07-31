package kz.akvelon.twitter.security.config;

import kz.akvelon.twitter.security.filters.JwtAuthenticationFilter;
import kz.akvelon.twitter.security.filters.JwtAuthorizationFilter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;


@RequiredArgsConstructor
@EnableWebSecurity
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Profile("!noSecurity")
public class JwtSecurityConfig {

    UserDetailsService userDetailsServiceImpl;

    PasswordEncoder passwordEncoder;

    AuthenticationProvider refreshTokenAuthenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity,
                                                   JwtAuthenticationFilter jwtAuthenticationFilter,
                                                   JwtAuthorizationFilter jwtAuthorizationFilter) throws Exception {
        httpSecurity.csrf().disable();
        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        httpSecurity.authorizeRequests().antMatchers("/registration/**", "auth/token/**", "/", "/twitter-open-api").permitAll();
        httpSecurity.authorizeRequests().antMatchers("/users/**", "/tweets/**", "/tags/**").authenticated();
        httpSecurity.authorizeRequests().antMatchers("/admin/**", "/roles/**").hasAuthority("ADMIN");


        httpSecurity.addFilter(jwtAuthenticationFilter);
        httpSecurity.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();

    }


    @Autowired
    public void bindUserDetailsServiceAndPasswordEncoder(AuthenticationManagerBuilder builder) throws Exception {
        builder.authenticationProvider(refreshTokenAuthenticationProvider);
        builder.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder);
    }

}

