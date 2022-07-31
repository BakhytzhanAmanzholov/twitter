package kz.akvelon.twitter.service.implementation;

import kz.akvelon.twitter.model.BlackList;
import kz.akvelon.twitter.repository.BlackListRepository;
import kz.akvelon.twitter.service.BlackListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BlackListServiceImpl implements BlackListService {

    private final BlackListRepository repository;

    @Override
    public BlackList save(BlackList entity) {
        return repository.save(entity);
    }

    @Override
    public void delete(Long id) {
        BlackList blackList = findById(id);
        repository.delete(blackList);
    }

    @Override
    public BlackList update(BlackList entity) {
        BlackList blackList = findById(entity.getId());
        blackList.setWord(entity.getWord());
        return blackList;
    }

    @Override
    public BlackList findById(Long id) {
        return repository.findById(id).orElseThrow(IllegalArgumentException::new);
    }
}
