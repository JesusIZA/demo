package com.example.demo.dao;

public interface Dao <T> {
    public T get(Integer id);
    public void insert(T entity);
}
