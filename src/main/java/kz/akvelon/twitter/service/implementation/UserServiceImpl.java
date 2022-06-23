package kz.akvelon.twitter.service.implementation;

import kz.akvelon.twitter.model.Account;
import kz.akvelon.twitter.model.Role;
import kz.akvelon.twitter.model.Tweet;
import kz.akvelon.twitter.repository.RoleRepository;
import kz.akvelon.twitter.repository.UserRepository;
import kz.akvelon.twitter.service.TweetService;
import kz.akvelon.twitter.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final TweetService tweetService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account user = userRepository.findByEmail(email);
        if (user == null) {
            log.error("User not found");
            throw new UsernameNotFoundException("User not found");
        } else {
            log.info("User found {}", user.getEmail());
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }

    @Override
    public Account findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Account save(Account user) {
        log.info("Saving new User {}", user.getEmail());
        Account userFinal = userRepository.save(user);
        addRoleToUser(user.getEmail(), "ROLE_USER");
        return userFinal;
    }

    @Override
    public void addRoleToUser(String email, String roleName) {
        log.info("Adding role {} to user {}", roleName, email);
        Account account = userRepository.findByEmail(email);
        Role role = roleRepository.findByName(roleName);
        account.getRoles().add(role);
        System.out.println(account);
    }

    @Override
    public void delete(Long id) {
        Account account = findById(id);
        userRepository.delete(account);
    }

    @Override
    public Account update(Account entity) {
        Account account = findByEmail(entity.getEmail());
        account.setUsername(entity.getUsername());
        return account;
    }

    @Override
    public Account findById(Long aLong) {
        return userRepository.findById(aLong).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public void addTweetToUser(Long tweetId, String email) {
        Account account = findByEmail(email);
        Tweet tweet = tweetService.findById(tweetId);
        account.getTweets().add(tweet);
    }

    @Override
    public void retweet(Long tweetId, String email) {
        Account account = findByEmail(email);
        Tweet tweet = tweetService.findById(tweetId);
        if (account.getTweets().contains(tweet)) {
            throw new IllegalArgumentException();
        }
        account.getRetweets().add(tweet);
    }

    @Override
    public void quote(Long tweetId, String email, Tweet tweet) {
        Account account = findByEmail(email);
        Tweet entity = tweetService.findById(tweetId);
        tweet.setQuoteTweet(entity);
        account.getQuoteTweets().add(tweet);
        tweet.setAccount(account);
        tweetService.save(tweet);
    }

    @Override
    public String isLogged() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        log.info(currentPrincipalName);
        if (!currentPrincipalName.equals("anonymousUser")) {
            return currentPrincipalName;
        }
        return "anonymousUser";
    }
}
