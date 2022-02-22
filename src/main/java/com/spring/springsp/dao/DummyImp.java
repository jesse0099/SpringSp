package com.spring.springsp.dao;

import com.spring.springsp.models.Dummy;
import com.spring.springsp.models.Usuario;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class DummyImp implements DummyDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Dummy> get() {
        return entityManager.createNamedQuery("Dummy.getAll",Dummy.class).getResultList();
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void create(Dummy dummy) {

    }

    @Override
    public Dummy getById(int id) {
        return entityManager.createNamedQuery("Dummy.getById", Dummy.class)
                .setParameter("idParam",id)
                .getSingleResult();
    }
}
