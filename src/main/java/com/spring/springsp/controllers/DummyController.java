package com.spring.springsp.controllers;

import com.spring.springsp.dao.DummyDao;
import com.spring.springsp.models.Dummy;
import com.spring.springsp.utils.RestPreconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/dummies")
public class DummyController {

    //Anotacion para crear automaticamente un objeto dao
    //y aplicarle un patron singleton
    @Autowired
    private DummyDao dao;

    @GetMapping
    public List<Dummy> getDummies(){
        return dao.get();
    }

    @RequestMapping(value = "/{id}")
    public Dummy getById(@PathVariable("id") int id){
        return RestPreconditions.checkFound(dao.getById(id));
    }

}
