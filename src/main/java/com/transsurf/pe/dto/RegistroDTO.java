package com.transsurf.pe.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter @Setter
@NoArgsConstructor
public class RegistroDTO {

    private int idDocumento;
    private String numDoc;
    private String nombre;
    private String apellido;
    private Date fechaNacimiento;
    private String email;
    private String password;
    private String celular;
    private String estado;

}
