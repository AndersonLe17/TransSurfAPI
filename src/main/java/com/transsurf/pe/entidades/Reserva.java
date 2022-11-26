package com.transsurf.pe.entidades;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor
@Entity @Table(name = "reserva")
public class Reserva {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReserva;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idAsientoProg",nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private AsientoProgramacion asientoProgramacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUsuario", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Usuario usuario;

    @Column(name = "fecha",nullable = false)
    private Date fecha;
    @Column(name="hora", nullable = false)
    private Time hora;

}
