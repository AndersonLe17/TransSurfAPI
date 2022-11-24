package com.transsurf.pe.repositorio;

import com.transsurf.pe.entidades.AsientoProgramacion;
import com.transsurf.pe.entidades.Programacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AsientoProgramacionRepositorio extends JpaRepository<AsientoProgramacion,Long> {

    public List<AsientoProgramacion> findAllByProgramacion(Programacion programacion);

}
