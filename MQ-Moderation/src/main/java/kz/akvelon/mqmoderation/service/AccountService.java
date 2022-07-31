package kz.akvelon.mqmoderation.service;

import kz.akvelon.mqmoderation.models.Account;

public interface AccountService {
    void ban(String email);
    Account findByID(Long id);

    Account findByEmail(String email);
}
