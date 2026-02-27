package com.hit.dao;
import java.util.List;

public interface IDao<T> {
    void saveAll(List<T> entities);
    List<T> getAll();
    boolean save(T entity);
    boolean delete(T entity);
}