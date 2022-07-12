package kz.akvelon.twitter.service.implementation;

import kz.akvelon.twitter.model.Reaction;
import kz.akvelon.twitter.repository.ReactionRepository;
import kz.akvelon.twitter.service.ReactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ReactionServiceImpl implements ReactionService {
    private final ReactionRepository repository;

    @Override
    public Reaction save(Reaction entity) {
        return repository.save(entity);
    }

    @Override
    public void delete(Long aLong) {
        repository.delete(findById(aLong));
    }

    @Override
    public Reaction update(Reaction entity) {
        Reaction reaction = findById(entity.getId());
        reaction.setName(entity.getName());
        return reaction;
    }

    @Override
    public Reaction findById(Long id) {
        return repository.findById(id).orElseThrow(IllegalArgumentException::new);
    }
}
