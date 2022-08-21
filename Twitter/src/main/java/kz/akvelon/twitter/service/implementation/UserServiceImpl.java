package kz.akvelon.twitter.service.implementation;

import kz.akvelon.twitter.model.*;
import kz.akvelon.twitter.repository.RoleRepository;
import kz.akvelon.twitter.repository.UserRepository;
import kz.akvelon.twitter.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailSenderService emailSenderService;

    private final FeedService feedService;


    @Override
    public Account findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User <" + email + "> not found"));
    }

    @Override
    public Account save(Account user) {
        log.info("Saving new User {}", user.getEmail());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setConfirmed(false);
        emailSenderService.sendEmail(user.getEmail(), user.getUsername());
        Account userFinal = userRepository.save(user);

        userFinal.setFeedTweets(feedService.save(
                FeedTweets.builder()
                        .tweetList(new HashSet<>())
                        .account(user)
                        .build()
        ));
        return userFinal;
    }

    @Override
    public void addRoleToUser(String email, String roleName) {
        log.info("Adding role {} to user {}", roleName, email);
        Account account = userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User <" + email + "> not found"));;
        Role role = roleRepository.findByName(roleName);
        account.setRoles(new ArrayList<>());
        account.getRoles().add(role);
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
    public String isLogged() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        log.info(currentPrincipalName);
        if (!currentPrincipalName.equals("anonymousUser")) {
            return currentPrincipalName;
        }
        return "anonymousUser";
    }


    @Override
    public boolean subscribe(Long id, String email) {
        Account subscriber = findByEmail(email);
        Account account = findById(id);

        if (subscriber.getListSubscriptions().contains(account) || subscriber.equals(account)) {
            return false;
        }

        subscriber.getListSubscriptions().add(account);
        subscriber.setSubscriptions(subscriber.getSubscriptions() + 1);

        account.getListSubscribers().add(subscriber);
        account.setSubscribers(account.getSubscribers() + 1);

        return true;
    }

    @Override
    public void confirm(String email, String username) {
        Account account = findByEmail(email);
        account.setConfirmed(true);
        addRoleToUser(account.getEmail(), "USER");
    }

}
