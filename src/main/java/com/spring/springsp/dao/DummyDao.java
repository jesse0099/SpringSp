package com.spring.springsp.dao;

import com.spring.springsp.models.Dummy;

public interface DummyDao extends IDao<Dummy>{
    public Dummy getById(int id);
}
