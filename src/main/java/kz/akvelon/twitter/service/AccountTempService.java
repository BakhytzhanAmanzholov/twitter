package kz.akvelon.twitter.service;

import kz.akvelon.twitter.model.AccountTemp;

public interface AccountTempService extends CrudService<AccountTemp, Long>{
    AccountTemp findByEmail(String email);
}
