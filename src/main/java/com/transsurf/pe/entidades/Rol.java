package com.transsurf.pe.entidades;

import lombok.*;

import javax.persistence.*;

@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "rol")
public class Rol {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idRol;

    @Column(length = 16,unique = true,nullable = false)
    private String tipo;

}
