package kz.akvelon.mqfeed.service.implementation;

import kz.akvelon.mqfeed.models.Account;
import kz.akvelon.mqfeed.models.FeedTweets;
import kz.akvelon.mqfeed.repository.AccountRepository;
import kz.akvelon.mqfeed.service.AccountService;
import kz.akvelon.mqfeed.service.FeedPercentagesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    private final FeedPercentagesService feedPercentagesService;

    @Override
    public Account findByEmail(String email) {
        return accountRepository.findAccountByEmail(email).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public FeedTweets isExistFeed(Account account) {
        return account.getFeedTweets();
    }
}
