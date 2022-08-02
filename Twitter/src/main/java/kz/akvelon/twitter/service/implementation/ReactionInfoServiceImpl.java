package kz.akvelon.twitter.service.implementation;

import kz.akvelon.twitter.model.ReactionInfo;
import kz.akvelon.twitter.repository.ReactionInfoRepository;
import kz.akvelon.twitter.repository.ReactionRepository;
import kz.akvelon.twitter.service.ReactionInfoService;
import kz.akvelon.twitter.service.ReactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ReactionInfoServiceImpl implements ReactionInfoService {
    private final ReactionInfoRepository repository;

    @Override
    public ReactionInfo save(ReactionInfo entity) {
        return repository.save(entity);
    }

    @Override
    public void delete(Long id) {
        repository.delete(findById(id));
    }

    @Override
    public ReactionInfo update(ReactionInfo entity) {
        ReactionInfo reaction = findById(entity.getId());
        reaction.setCount(entity.getCount() + 1);
        return reaction;
    }

    @Override
    public ReactionInfo findById(Long id) {
        return repository.findById(id).orElseThrow(IllegalArgumentException::new);
    }
}
