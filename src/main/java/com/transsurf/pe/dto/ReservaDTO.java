package com.transsurf.pe.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;
import java.sql.Time;

@Getter @Setter
@NoArgsConstructor @ToString
public class ReservaDTO {
    private Long idReserva;
    private AsientoProgramacionDTO asientoProgramacion;
    private UsuarioDTO usuario;
    private Date fecha;
    private Time hora;
}
