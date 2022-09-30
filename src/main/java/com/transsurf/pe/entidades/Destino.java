package com.transsurf.pe.entidades;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Getter @Setter @ToString
@NoArgsConstructor @RequiredArgsConstructor
@Entity
@Table(name = "destino")
public class Destino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDestino;

    @NonNull
    @OneToOne
    @JoinColumn(name = "idCiudad")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Ciudad ciudad;

}
