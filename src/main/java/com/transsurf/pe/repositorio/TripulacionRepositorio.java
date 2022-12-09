package com.transsurf.pe.repositorio;

import com.transsurf.pe.entidades.Programacion;
import com.transsurf.pe.entidades.Tripulacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TripulacionRepositorio extends JpaRepository<Tripulacion, Integer> {
    List<Tripulacion> findAllByProgramacion(Programacion programacion);

    @Query(value = "SELECT t.usuario as usuario, count(t.usuario) as cantidad FROM Tripulacion t group by usuario")
    public List<Object> findReportTripulacion();
}
