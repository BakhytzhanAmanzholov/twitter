package kz.akvelon.twitter.service.implementation;

import kz.akvelon.twitter.model.Account;
import kz.akvelon.twitter.model.AccountTemp;
import kz.akvelon.twitter.repository.AccountTempRepository;
import kz.akvelon.twitter.service.AccountTempService;
import kz.akvelon.twitter.service.EmailSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AccountTempServiceImpl implements AccountTempService {
    private final PasswordEncoder passwordEncoder;
    private final EmailSenderService emailSenderService;
    private final AccountTempRepository accountTempRepository;

    @Override
    public AccountTemp save(AccountTemp accountTemp) {
        log.info("Saving new User {}, before confirm email", accountTemp.getEmail());
        accountTemp.setPassword(passwordEncoder.encode(accountTemp.getPassword()));
        emailSenderService.sendEmail(accountTemp.getEmail(), accountTemp.getUsername());
        return accountTempRepository.save(accountTemp);
    }

    @Override
    public AccountTemp findByEmail(String email) {
        return accountTempRepository.findByEmail(email);
    }

    @Override
    public void delete(AccountTemp entity) {
        accountTempRepository.delete(entity);
    }

    @Override
    public AccountTemp update(AccountTemp entity) {
        AccountTemp accountTemp = findById(entity.getId());
        accountTemp.setEmail(entity.getEmail());
        accountTemp.setUsername(entity.getUsername());
        emailSenderService.sendEmail(accountTemp.getEmail(), accountTemp.getUsername());
        return accountTemp;
    }

    @Override
    public AccountTemp findById(Long aLong) {
        return accountTempRepository.findById(aLong).orElseThrow(IllegalArgumentException::new);
    }
}
