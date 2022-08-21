package kz.akvelon.twitter.service;

import kz.akvelon.twitter.model.BlackList;

public interface AdminService {
    BlackList save(BlackList entity);

    String ban(String email);

    void addRoleToUser(String email, String roleName);
}
