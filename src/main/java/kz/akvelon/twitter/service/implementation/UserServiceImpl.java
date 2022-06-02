package kz.akvelon.twitter.service.implementation;

import kz.akvelon.twitter.model.Account;
import kz.akvelon.twitter.model.Role;
import kz.akvelon.twitter.repository.RoleRepository;
import kz.akvelon.twitter.repository.UserRepository;
import kz.akvelon.twitter.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
        System.out.println(user.toString());
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }

    @Override
    public Account findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Account save(Account user) {
        log.info("Saving new User {}", user.getEmail());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Account userFinal = userRepository.save(user);
        addRoleToUser(user.getEmail(), "ROLE_USER");
        return userFinal;
    }

    @Override
    public void addRoleToUser(String email, String roleName) {
        log.info("Adding role {} to user {}", roleName, email);
        Account account = userRepository.findByEmail(email);
        Role role = roleRepository.findByName(roleName);
        System.out.println(role);
        account.getRoles().add(role);
        System.out.println(account);
    }
}
