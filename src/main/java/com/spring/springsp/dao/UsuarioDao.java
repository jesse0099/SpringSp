package com.spring.springsp.dao;

import com.spring.springsp.models.Usuario;

public interface UsuarioDao extends IDao<Usuario>{
    Usuario userByEmail(Usuario usuario);
}
