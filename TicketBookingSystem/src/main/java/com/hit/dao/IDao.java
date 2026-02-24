package com.hit.dao;
import java.util.List;

public interface IDao<T> {
    void saveAll(List<T> entities);
    List<T> getAll();
    boolean save(T entity);   // הוספנו כדי שה-Service יזהה
    boolean delete(T entity); // הוספנו כדי שה-Service יזהה
}