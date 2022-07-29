package kz.akvelon.twitter.service;

import kz.akvelon.twitter.model.Account;
import kz.akvelon.twitter.model.Tweet;

public interface UserService extends CrudService<Account, Long>{
    Account findByEmail(String email);
    void addRoleToUser(String email, String roleName);

    void addTweetToUser(Long tweetId, String email);

    void retweet(Long tweetId, String email);

    void quote(Long tweetId, String email, Tweet tweet);
    String isLogged();

    boolean reaction(Long tweetId, String email, Long reactionId);

    boolean subscribe(Long id, String email);

    void confirm(String email, String username);

    void ban(String email);

}
