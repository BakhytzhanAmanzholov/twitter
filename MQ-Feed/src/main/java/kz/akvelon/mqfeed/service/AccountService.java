package kz.akvelon.mqfeed.service;

import kz.akvelon.mqfeed.models.Account;
import kz.akvelon.mqfeed.models.FeedTweets;

public interface AccountService {

    Account findByEmail(String email);

    FeedTweets isExistFeed(Account account);
}
