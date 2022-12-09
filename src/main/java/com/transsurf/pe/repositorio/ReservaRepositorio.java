package com.transsurf.pe.repositorio;

import com.transsurf.pe.entidades.AsientoProgramacion;
import com.transsurf.pe.entidades.Reserva;
import com.transsurf.pe.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.util.List;

public interface ReservaRepositorio extends JpaRepository<Reserva, Long> {

    public List<Reserva> findAllByUsuario(Usuario usuario);

    public Reserva findByAsientoProgramacion(AsientoProgramacion asientoProgramacion);

    public List<Reserva> findAllByAsientoProgramacionIn(List<AsientoProgramacion> asientoProgramacions);

    public List<Reserva> findAllByFechaAfter(Date date);

    @Query(value = "SELECT SUM(r.asientoProgramacion.programacion.costo) AS ingreso, MONTH(r.fecha) AS mes FROM Reserva r GROUP BY mes")
    public List<Object> findReport();

}
