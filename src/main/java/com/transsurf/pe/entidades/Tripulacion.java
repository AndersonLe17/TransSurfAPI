package com.transsurf.pe.entidades;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor @RequiredArgsConstructor
@Entity @Table(name = "tripulacion")
public class Tripulacion {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idTripulacion;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idProgramacion",nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Programacion programacion;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUsuario",nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Usuario usuario;
}
