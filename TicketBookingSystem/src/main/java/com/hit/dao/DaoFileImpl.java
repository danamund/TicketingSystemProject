package com.hit.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DaoFileImpl<T> implements IDao<T> {
    private String filePath;
    private Gson gson;
    private Class<T> type;

    public DaoFileImpl(String filePath, Class<T> type) {
        this.filePath = filePath;
        this.type = type;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @Override
    public void saveAll(List<T> entities) {
        try (Writer writer = new FileWriter(filePath)) {
            gson.toJson(entities, writer);
        } catch (IOException e) { e.printStackTrace(); }
    }

    @Override
    public List<T> getAll() {
        try (Reader reader = new FileReader(filePath)) {
            Type listType = TypeToken.getParameterized(List.class, type).getType();
            List<T> result = gson.fromJson(reader, listType);
            System.out.println("DAO loaded " + (result != null ? result.size() : 0) + " items."); // הדפסת בקרה
            return result != null ? result : new ArrayList<>();
        } catch (IOException e) {
            System.err.println("CRITICAL: Could not find or read file at: " + filePath);
            return new ArrayList<>();
        }
    }
    @Override
    public boolean save(T entity) {
        List<T> all = getAll();
        all.add(entity);
        saveAll(all);
        return true;
    }

    @Override
    public boolean delete(T entity) {
        List<T> all = getAll();
        boolean removed = all.remove(entity);
        if (removed) {
            saveAll(all);
        }
        return removed;
    }
}