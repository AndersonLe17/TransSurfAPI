package com.transsurf.pe.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class AsientoDTO {
    private Long idAsiento;
    private UnidadDTO unidad;
    private NivelDTO nivel;
    private int numAsiento;
    private String estado;
}
