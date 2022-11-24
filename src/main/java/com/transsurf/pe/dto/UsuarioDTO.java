package com.transsurf.pe.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;
import java.util.Set;

@Getter @Setter
@NoArgsConstructor
@ToString
public class UsuarioDTO {
    private Long idUsuario;
    private DocumentoDTO documento;
    private Set<RolDTO> roles;
    private String numDoc;
    private String nombre;
    private String apellido;
    private Date fechaNacimiento;
    private String email;
    private String password;
    private String celular;
    private String estado;

}
