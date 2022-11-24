package com.transsurf.pe.entidades;

import lombok.*;

import javax.persistence.*;

@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "nivel")
public class Nivel {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idNivel;

    @Column(length = 20,unique = true,nullable = false)
    private String nivel;
}
