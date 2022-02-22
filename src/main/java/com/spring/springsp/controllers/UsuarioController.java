package com.spring.springsp.controllers;

import com.spring.springsp.models.*;
import com.spring.springsp.service.UsuarioRepo;
import com.spring.springsp.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UsuarioController {

    //Anotacion para crear automaticamente un objeto Usuarioservice
    //y aplicarle un patron singleton
    @Qualifier("UsuarioRepoImp")
    @Autowired
    private UsuarioRepo service;

    @Autowired
    private JWTUtil jwtUtil2;


    @RequestMapping(value = "api/usuarios/{id}", method = RequestMethod.GET)
    public Usuario getUser(@PathVariable Long id) {
        return new Usuario() {{
            setId(id);
            setNombre("Jese");
            setApellido("Chavez");
            setEmail("jesechavez009@gmail.com");
            setTelefono("89568925");
        }};
    }

    //  @PreAuthorize("hasRole('ADMIN')")
//  Dejar solo el @Request header da acceso independiente del rol, basta
//  con que el token sea valido
//  @PreAuthorize chequea el token y el rol
    @RequestMapping(value = "api/usuarios", method = RequestMethod.GET)
    public List<Usuario> getUsers(@RequestHeader("Authorization") String token) {
        return service.get();
    }

    @RequestMapping(value = "api/usuarios/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@RequestHeader("Authorization") String token, @PathVariable Long id) {
//        if(jwtUtil2.validateToken(token))
        service.delete(id);
    }

    /**
     * Method to create a new user
     *
     * @param usuario
     * @return ResponseBody
     */
    //@PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "api/usuarios", method = RequestMethod.POST)
    public ResponseEntity<?> createUser(@RequestBody Usuario usuario) {
        service.create(usuario);
        return ResponseEntity.ok(usuario);
    }

}
