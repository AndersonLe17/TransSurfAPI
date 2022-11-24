package com.transsurf.pe.entidades;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;

@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "unidad")
public class Unidad {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUnidad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idModelo",nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Modelo modelo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idMarca",nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Marca marca;

    @Column(length = 7, unique = true,nullable = false)
    private String placa;
    @Column(nullable = false)
    private int numAsientos;
    @Column(nullable = false)
    private int numPisos;
    @Column(length = 15,nullable = false)
    private String estado;
}
