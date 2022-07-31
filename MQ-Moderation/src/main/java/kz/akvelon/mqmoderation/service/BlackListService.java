package kz.akvelon.mqmoderation.service;

import kz.akvelon.mqmoderation.models.BlackList;

import java.util.List;

public interface BlackListService {
    List<BlackList> findAll();
}
