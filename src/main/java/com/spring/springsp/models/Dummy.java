package com.spring.springsp.models;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "dummies")
@NamedQueries({
        @NamedQuery(name="Dummy.getAll", query = "SELECT u FROM Dummy u"),
        @NamedQuery(name="Dummy.getById", query = "SELECT u FROM Dummy u WHERE u.id = :idParam")
})
@ToString
@EqualsAndHashCode
public class Dummy {

    @Id
    @Basic(optional = false)
    @Getter
    @Setter
    @Column(name = "id", nullable = false)
    private Long id;

    @Getter @Setter @Column(name = "dummy")
    private String dummy;

}
