package com.transsurf.pe.servicio;

import com.transsurf.pe.dto.TripulacionDTO;
import com.transsurf.pe.entidades.Programacion;
import com.transsurf.pe.entidades.Tripulacion;
import com.transsurf.pe.entidades.Usuario;

import java.util.List;

public interface TripulacionServicio {
    public void crearTripulacion(Programacion programacion, List<Usuario> usuarios);

    public List<TripulacionDTO> getTripuacionByProgramacion(Programacion programacion);

    public void eliminarTripulacionByProgramacion(Programacion oldProgramacion);
}
