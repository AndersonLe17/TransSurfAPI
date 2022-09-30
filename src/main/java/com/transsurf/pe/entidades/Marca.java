package com.transsurf.pe.entidades;

import lombok.*;

import javax.persistence.*;

@Getter @Setter @ToString
@NoArgsConstructor
@Entity
@Table(name = "marca")
public class Marca {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idMarca;

    @Column(length = 50,unique = true,nullable = false)
    private String nombre;

    @Column(length = 15,nullable = false)
    private String estado;

}
