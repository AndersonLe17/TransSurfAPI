package com.transsurf.pe.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class AsientoProgramacionDTO {
    private Long idAsientoProg;
    private ProgramacionDTO programacion;
    private AsientoDTO asiento;
    private String estado;
}
