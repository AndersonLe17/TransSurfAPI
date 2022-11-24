package com.transsurf.pe.entidades;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "programacion")
public class Programacion {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idProgramacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUnidad",nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Unidad unidad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idOrigen",nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Origen origen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idDestino",nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Destino destino;

    @Column(name="fecha", nullable = false)
    private Date fecha;
    @Column(name="hora", nullable = false)
    private Time hora;
    @Column(nullable = false)
    private Double costo;
    @Column(length = 15,nullable = false)
    private String estado;
}
