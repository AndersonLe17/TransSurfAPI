package com.transsurf.pe.repositorio;

import com.transsurf.pe.entidades.Destino;
import com.transsurf.pe.entidades.Origen;
import com.transsurf.pe.entidades.Programacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface ProgramacionRepositorio extends JpaRepository<Programacion, Integer> {

    public List<Programacion> findAllByOrigenAndDestinoAndFecha(Origen origen, Destino destino, Date fecha);

}
