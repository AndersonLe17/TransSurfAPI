package com.transsurf.pe.servicio;

import com.transsurf.pe.entidades.Asiento;
import com.transsurf.pe.entidades.Unidad;

import java.util.List;

public interface AsientoServicio {
    public void crearAsientos(Unidad unidad);

    public void actualizarAsientos(Unidad unidad);

    public List<Asiento> obtenerAsientosByUnidad(Unidad unidad);
}
