package com.transsurf.pe.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;

@Getter @Setter
@NoArgsConstructor
public class ProgramacionDTO {
    private int idProgramacion;
    private UnidadDTO unidad;
    private OrigenDTO origen;
    private DestinoDTO destino;
    private Date fecha;
    private Time hora;
    private Double costo;
    private String estado;
}
