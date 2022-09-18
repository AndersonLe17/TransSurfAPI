package com.transsurf.pe.dto;

import com.transsurf.pe.entidades.Usuario;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter @Setter
@NoArgsConstructor
public class RolDTO {
    private int idRol;
    private String tipo;
    private Set<Usuario> usuarios;
}
