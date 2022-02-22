package com.spring.springsp.dao;

import java.util.List;

public interface IDao<T> {
    List<T> get();
    void delete(Long id);
    void create(T t);
}
