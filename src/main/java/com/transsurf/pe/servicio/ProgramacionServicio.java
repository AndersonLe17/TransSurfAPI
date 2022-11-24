package com.transsurf.pe.servicio;

import com.transsurf.pe.dto.ProgramacionDTO;
import com.transsurf.pe.entidades.Destino;
import com.transsurf.pe.entidades.Origen;
import com.transsurf.pe.entidades.Unidad;
import com.transsurf.pe.entidades.Usuario;

import java.sql.Date;
import java.util.List;

public interface ProgramacionServicio {
    public List<ProgramacionDTO> obtenerProgramaciones();

    public ProgramacionDTO crearProgramacion(ProgramacionDTO programacionDTO, Unidad unidad, Origen origen, Destino destino, List<Usuario> usuarios);

    public ProgramacionDTO getProgramacionById(int idProgramacion);

    public ProgramacionDTO modificarProgramacion(ProgramacionDTO programacionDTO, Unidad unidad, Origen origen, Destino destino, List<Usuario> usuarios, int idProgramacion);

    public void eliminarProgramacion(int idProgramacion);

    public List<ProgramacionDTO> obtenerProgramacionesByOrigenAndDestinoAndFecha(Origen origen, Destino destino, Date fechaIda);
}
