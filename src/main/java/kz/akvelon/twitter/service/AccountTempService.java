package kz.akvelon.twitter.service;

import kz.akvelon.twitter.model.AccountTemp;

public interface AccountTempService  {
    AccountTemp save(AccountTemp accountTemp);
    AccountTemp findByEmail(String email);
    void delete(AccountTemp accountTemp);
}
