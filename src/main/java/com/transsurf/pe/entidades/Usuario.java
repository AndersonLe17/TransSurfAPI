package com.transsurf.pe.entidades;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idDocumento",nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Documento documento;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_rol", joinColumns = @JoinColumn(name = "idUsuario", referencedColumnName = "idUsuario"),
    inverseJoinColumns = @JoinColumn(name = "idRol", referencedColumnName = "idRol"))
    private Set<Rol> roles = new HashSet<>();

    @Column(length = 12,unique = true,nullable = false)
    private String numDoc;
    @Column(length = 100,nullable = false)
    private String nombre;
    @Column(length = 100,nullable = false)
    private String apellido;
    @Column(name = "fechaNacimiento",nullable = false)
    private Date fechaNacimiento;
    @Column(length = 255,unique = true,nullable = false)
    private String email;
    @Column(length = 255,nullable = false)
    private String password;
    @Column(length = 9,nullable = false)
    private String celular;
    @Column(length = 15,nullable = false)
    private String estado;

}
