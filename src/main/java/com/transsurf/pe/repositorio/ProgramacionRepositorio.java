package com.transsurf.pe.repositorio;

import com.transsurf.pe.entidades.Destino;
import com.transsurf.pe.entidades.Origen;
import com.transsurf.pe.entidades.Programacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface ProgramacionRepositorio extends JpaRepository<Programacion, Integer> {

    public List<Programacion> findAllByOrigenAndDestinoAndFechaAndEstado(Origen origen, Destino destino, Date fecha, String estado);

    public List<Programacion> findAllByEstadoOrEstadoIs(String estado1, String estado2);

    @Query(value = "SELECT p.unidad.placa as und, count(p.unidad) as cantidad FROM Programacion p group by und")
    public List<Object> findReportUnidades();

    @Query(value = "SELECT p.destino.ciudad.nombre as ciudad, count(p.destino) as cant FROM Programacion p group by ciudad")
    public List<Object> findReportCiudades();
}
