package kz.akvelon.twitter.service;

import kz.akvelon.twitter.dto.RegistrationDto;
import kz.akvelon.twitter.model.Account;

public interface UserService extends CrudService<Account, Long>{
    Account findByEmail(String email);
    void addRoleToUser(String email, String roleName);
}
