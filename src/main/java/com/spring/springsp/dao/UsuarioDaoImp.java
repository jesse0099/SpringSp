package com.spring.springsp.dao;

import com.spring.springsp.models.Usuario;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class UsuarioDaoImp implements UsuarioDao{

    //Lista con items del ultimo fetch

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Usuario> get() {
        return entityManager.createNamedQuery("Usuario.getAll",Usuario.class).getResultList();
    }

    @Override
    public void delete(Long id) {
        entityManager.remove(entityManager.find(Usuario.class,id));
    }

    @Override
    public void create(Usuario usuario) {
        entityManager.persist(usuario);
    }

    @Override
    public Usuario userByEmail(Usuario usuario) {
        List<Usuario> user = entityManager.createNamedQuery("Usuario.checkEmail", Usuario.class)
                .setParameter("emailParam", usuario.getEmail())
                .getResultList();
        if(user.isEmpty())
            return null;
        return user.get(0);
    }


}
