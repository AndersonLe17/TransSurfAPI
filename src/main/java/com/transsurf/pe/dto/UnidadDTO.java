package com.transsurf.pe.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class UnidadDTO {
    private int idUnidad;
    private ModeloDTO modelo;
    private MarcaDTO marca;
    private String placa;
    private int numAsientos;
    private int numPisos;
    private String estado;
}
