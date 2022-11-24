package com.transsurf.pe.entidades;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "asiento")
public class Asiento {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAsiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUnidad",nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Unidad unidad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idNivel",nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Nivel nivel;

    @Column(nullable = false)
    private int numAsiento;
    @Column(length = 15,nullable = false)
    private String estado;
}
