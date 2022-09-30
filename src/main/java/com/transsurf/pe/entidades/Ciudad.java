package com.transsurf.pe.entidades;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "ciudad")
public class Ciudad {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCiudad;
    @Column(length = 50,unique = true,nullable = false)
    private String nombre;
    @Column(length = 15,nullable = false)
    private String estado;

}
