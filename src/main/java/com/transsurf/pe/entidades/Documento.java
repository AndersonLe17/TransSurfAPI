package com.transsurf.pe.entidades;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter @Setter @ToString
@NoArgsConstructor
@Entity
@Table(name = "documento")
public class Documento {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idDocumento;

    @Column(length = 5,unique = true,nullable = false)
    private String tipo;
    @OneToMany(mappedBy = "documento", cascade = CascadeType.ALL)
    private Set<Usuario> usuarios = new HashSet<>();
}
