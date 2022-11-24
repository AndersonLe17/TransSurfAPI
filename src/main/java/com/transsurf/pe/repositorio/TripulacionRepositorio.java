package com.transsurf.pe.repositorio;

import com.transsurf.pe.entidades.Programacion;
import com.transsurf.pe.entidades.Tripulacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TripulacionRepositorio extends JpaRepository<Tripulacion, Integer> {
    List<Tripulacion> findAllByProgramacion(Programacion programacion);
}
