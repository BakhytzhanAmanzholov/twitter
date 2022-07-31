package kz.akvelon.mqmoderation.service.implementation;

import kz.akvelon.mqmoderation.models.Account;
import kz.akvelon.mqmoderation.repositories.AccountRepository;
import kz.akvelon.mqmoderation.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;

    @Override
    public void ban(String email) {
        Account account = findByEmail(email);
        account.setBanned(false);
    }

    @Override
    public Account findByID(Long id) {
        return repository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public Account findByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(IllegalArgumentException::new);
    }
}
