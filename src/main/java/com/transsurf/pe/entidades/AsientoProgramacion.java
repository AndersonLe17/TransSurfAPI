package com.transsurf.pe.entidades;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "asiento_programacion")
public class AsientoProgramacion {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAsientoProg;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idProgramacion",nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Programacion programacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idAsiento",nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Asiento asiento;

    @Column(length = 15,nullable = false)
    private String estado;
}
