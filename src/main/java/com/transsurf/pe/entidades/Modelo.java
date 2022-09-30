package com.transsurf.pe.entidades;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter @Setter @ToString
@NoArgsConstructor
@Entity
@Table(name = "modelo")
public class Modelo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idModelo;

    @Column(length = 25,unique = true,nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String descripcion;

    @Column(length = 15,nullable = false)
    private String estado;

}
