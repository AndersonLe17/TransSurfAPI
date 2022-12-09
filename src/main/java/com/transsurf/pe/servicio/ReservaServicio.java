package com.transsurf.pe.servicio;

import com.transsurf.pe.dto.ReservaDTO;
import com.transsurf.pe.entidades.AsientoProgramacion;
import com.transsurf.pe.entidades.Usuario;

import java.util.List;

public interface ReservaServicio {
    public ReservaDTO crearReserva(AsientoProgramacion asientoProgramacion, Usuario usuario);

    public List<ReservaDTO> listarReservasByUsuario(Usuario usuario);

    public ReservaDTO getReservaById(Long idReserva);

    public ReservaDTO getReservaByasientoProgramacion(AsientoProgramacion asientoProgramacion);

    public List<ReservaDTO> listarReservasByAsientosProgramacions(List<AsientoProgramacion> asientoProgramacions);
}
