package kz.akvelon.twitter.service;

public interface CrudService <T, ID>{
    T save(T entity);
}
