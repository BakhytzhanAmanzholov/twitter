package kz.akvelon.twitter.service;

import kz.akvelon.twitter.model.BlackList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlackListService extends CrudService<BlackList, Long> {
}
