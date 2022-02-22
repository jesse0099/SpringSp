package com.spring.springsp.service;

import com.spring.springsp.dao.UsuarioDao;
import com.spring.springsp.models.MyUserDetails;
import com.spring.springsp.models.Usuario;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service("UsuarioRepoImp")
public class UsuarioRepoImp implements  UsuarioRepo, UserDetailsService {
    @Autowired
    private UsuarioDao dao;

    public UsuarioRepoImp() {
    }

    @Override
    public List<Usuario> get() {
        return dao.get();
    }

    @Override
    public void delete(Long id) {
        dao.delete(id);
    }

    @Override
    public void create(Usuario usuario) {
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        //Param 1 = iteraciones del hash , Param 2 = Memoria, Param 3 = Hilos de ejecucion, Param 4 = password para hashear
        String passHashed = argon2.hash(1,1024,1, usuario.getPassword());
        usuario.setPassword(passHashed);
        dao.create(usuario);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Usuario fetchedUser;
        Usuario myUser = new Usuario();

        myUser.setEmail(s);

        fetchedUser = dao.userByEmail(myUser);

        if(fetchedUser == null)
            throw new  UsernameNotFoundException("El usuario "+myUser.getEmail()+" no fue encontrado!");

        return new MyUserDetails(fetchedUser);
    }

}
