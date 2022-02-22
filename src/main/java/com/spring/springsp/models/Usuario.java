package com.spring.springsp.models;



import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "usuarios")
@NamedQueries({
        @NamedQuery(name=  "Usuario.getAll", query = "SELECT u FROM Usuario u")
        ,@NamedQuery(name= "Usuario.checkEmail", query="SELECT u FROM Usuario u WHERE u.email = :emailParam")
        ,
})
@ToString @EqualsAndHashCode
public class Usuario {

    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter @Column(name = "id", nullable = false)
    private Long id;

    @Getter @Setter @Column(name = "nombre")
    private String nombre;

    @Getter @Setter @Column(name = "apellido")
    private String apellido;

    @Getter @Setter @Column(name = "email")
    private String email;

    @Getter @Setter @Column(name = "telefono")
    private String telefono;

    @Getter @Setter @Column(name = "password")
    private String password;

    @Getter @Setter @Column(name = "role")
    private String roles;

    @Getter @Setter @Column(name = "active")
    private Boolean active;

}
