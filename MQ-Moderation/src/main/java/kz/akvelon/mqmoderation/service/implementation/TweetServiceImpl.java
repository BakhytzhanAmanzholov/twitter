package kz.akvelon.mqmoderation.service.implementation;

import kz.akvelon.mqmoderation.models.BlackList;
import kz.akvelon.mqmoderation.models.Tweet;
import kz.akvelon.mqmoderation.repositories.TweetRepository;
import kz.akvelon.mqmoderation.service.AccountService;
import kz.akvelon.mqmoderation.service.BlackListService;
import kz.akvelon.mqmoderation.service.TweetService;
import kz.akvelon.mqmoderation.util.TextSeparator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TweetServiceImpl implements TweetService {

    private final TweetRepository repository;

    private final TextSeparator separator;

    private final BlackListService blackListService;

    private final AccountService accountService;

    @Override
    public Tweet findById(Long id) {
        return repository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public boolean moderate(Long id) {
        Tweet tweet = findById(id);
        List<String> text = separator.separate(tweet.getText());

        List<BlackList> list = blackListService.findAll();
        List<String> blackList = new ArrayList<>();
        log.info(text.toString());

        for(BlackList word: list){
            blackList.add(word.getWord());
        }

        for (String str: text){
            if(blackList.contains(str)){
                update(id, "THIS MESSAGE IS BLOCKED");
                accountService.ban(tweet.getAccount().getEmail());
                return true;
            }
        }
        return false;
    }

    @Override
    public Tweet update(Long id, String text) {
        Tweet tweet = findById(id);
        tweet.setText(text);
        return tweet;
    }
}
