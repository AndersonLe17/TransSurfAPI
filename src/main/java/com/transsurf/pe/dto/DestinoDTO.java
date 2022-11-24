package com.transsurf.pe.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class DestinoDTO {
    private Long idDestino;
    private CiudadDTO ciudad;
}
