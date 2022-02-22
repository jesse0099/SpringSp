package com.spring.springsp.service;

import java.util.List;

public interface IRepo <T> {
    List<T> get();
    void delete(Long id);
    void create(T t);
}
