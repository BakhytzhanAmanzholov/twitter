package kz.akvelon.twitter.security.details;


import kz.akvelon.twitter.model.Account;
import kz.akvelon.twitter.model.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;


@RequiredArgsConstructor
@Slf4j
public class UserDetailsImpl implements UserDetails {

    private final Account account;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        for (Role role: account.getRoles()){
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getName());
            collection.add(authority);
        }
        return collection;
    }

    @Override
    public String getPassword() {
        return account.getPassword();
    }

    @Override
    public String getUsername() {
        return account.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return account.getBanned();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return account.getConfirmed();
    }

    public Account getUser() {
        return account;
    }

}
