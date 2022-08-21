package kz.akvelon.twitter.service.implementation;

import kz.akvelon.twitter.model.Account;
import kz.akvelon.twitter.model.BlackList;
import kz.akvelon.twitter.model.Role;
import kz.akvelon.twitter.model.Tweet;
import kz.akvelon.twitter.service.AdminService;
import kz.akvelon.twitter.service.BlackListService;
import kz.akvelon.twitter.service.TweetService;
import kz.akvelon.twitter.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminServiceImpl implements AdminService {

    private final UserService userService;

    private final BlackListService blackListService;

    private final TweetService tweetService;

    @Override
    public BlackList save(BlackList entity) {
        return blackListService.save(entity);
    }

    @Override
    public String ban(String email) {

        if (Objects.equals(email, userService.isLogged())) {
            return "You can't ban yourself";
        }

        Account account = userService.findByEmail(email);
        for(Role role: account.getRoles()){
            if(Objects.equals(role.getName(), "ADMIN")){
                return "You can't ban yourself";
            }
        }
        for (Tweet tweet: account.getTweets()) {
            tweetService.delete(tweet.getId());
        }
        account.setBanned(false);
        return "You have successfully banned";
    }

    @Override
    public void addRoleToUser(String email, String roleName) {
        userService.addRoleToUser(email, roleName);
    }
}
