package com.transsurf.pe.servicio;

import com.transsurf.pe.dto.ProgramacionDTO;
import com.transsurf.pe.dto.TripulacionDTO;
import com.transsurf.pe.entidades.Programacion;
import com.transsurf.pe.entidades.Tripulacion;
import com.transsurf.pe.entidades.Usuario;
import com.transsurf.pe.repositorio.TripulacionRepositorio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TripulacionServicioImp implements TripulacionServicio{

    @Autowired
    private TripulacionRepositorio tripulacionRepositorio;

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void crearTripulacion(Programacion programacion, List<Usuario> usuarios) {
        List<Tripulacion> tripulacions = new ArrayList<>();
        tripulacions.add(new Tripulacion(programacion, usuarios.get(0)));
        tripulacions.add(new Tripulacion(programacion, usuarios.get(1)));

        tripulacionRepositorio.saveAll(tripulacions);
        usuarioServicio.actualizarEstado(usuarios.get(0), "Asignado");
        usuarioServicio.actualizarEstado(usuarios.get(1), "Asignado");
    }

    @Override
    public List<TripulacionDTO> getTripuacionByProgramacion(Programacion programacion) {
        List<Tripulacion> tripulacions = tripulacionRepositorio.findAllByProgramacion(programacion);
        return tripulacions.stream().map(tripulacion -> mapearDTO(tripulacion)).collect(Collectors.toList());
    }

    @Override
    public void eliminarTripulacionByProgramacion(Programacion oldProgramacion) {
        List<Tripulacion> tripulacions = tripulacionRepositorio.findAllByProgramacion(oldProgramacion);
        usuarioServicio.actualizarEstado(tripulacions.get(0).getUsuario(), "Activo");
        usuarioServicio.actualizarEstado(tripulacions.get(1).getUsuario(), "Activo");

        tripulacionRepositorio.deleteAll(tripulacions);
    }

    // Convierte entidad a DTO
    private TripulacionDTO mapearDTO(Tripulacion tripulacion) {
        TripulacionDTO tripulacionDTO = modelMapper.map(tripulacion, TripulacionDTO.class);
        return tripulacionDTO;
    }
    // Convierte de DTO a Entidad
    private Tripulacion mapearEntidad(TripulacionDTO tripulacionDTO) {
        Tripulacion tripulacion = modelMapper.map(tripulacionDTO, Tripulacion.class);
        return tripulacion;
    }
}
