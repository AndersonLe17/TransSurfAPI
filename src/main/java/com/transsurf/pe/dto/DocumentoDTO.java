package com.transsurf.pe.dto;

import com.transsurf.pe.entidades.Usuario;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter @Setter
@NoArgsConstructor
public class DocumentoDTO {
    private int idDocumento;
    private String tipo;
    //private Set<Usuario> usuarios;

}
