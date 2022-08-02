package kz.akvelon.mqmoderation.service.implementation;

import kz.akvelon.mqmoderation.models.BlackList;
import kz.akvelon.mqmoderation.repositories.BlackListRepository;
import kz.akvelon.mqmoderation.service.BlackListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class BlackListServiceImpl implements BlackListService {

    private final BlackListRepository blackListRepository;


    @Override
    public List<BlackList> findAll() {
        return blackListRepository.findAll();
    }
}
