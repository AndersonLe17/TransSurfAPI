package com.transsurf.pe.servicio;

import com.transsurf.pe.dto.AsientoProgramacionDTO;
import com.transsurf.pe.entidades.AsientoProgramacion;
import com.transsurf.pe.entidades.Programacion;
import com.transsurf.pe.entidades.Unidad;

import java.util.List;

public interface AsientoProgramacionServicio {
    public void crearAsientosProgramacion(Programacion programacion, Unidad unidad);

    public void actualizarAsientosProgramacion(Programacion programacion, Unidad unidad, boolean sameUnidad);

    public List<AsientoProgramacionDTO> obtenerAsientosByProgramacion(Programacion programacion);

    public void actualizarEstadoAsiento(AsientoProgramacion asientoProgramacion, String estado);
}
