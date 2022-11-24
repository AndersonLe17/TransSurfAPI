package com.transsurf.pe.servicio;

import com.transsurf.pe.dto.ProgramacionDTO;
import com.transsurf.pe.entidades.*;
import com.transsurf.pe.repositorio.ProgramacionRepositorio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProgramacionServicioImp implements ProgramacionServicio{

    @Autowired
    private ProgramacionRepositorio programacionRepositorio;

    @Autowired
    private TripulacionServicio tripulacionServicio;

    @Autowired
    private UnidadServicio unidadServicio;

    @Autowired
    private AsientoProgramacionServicio asientoProgramacionServicio;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<ProgramacionDTO> obtenerProgramaciones() {
        List<Programacion> programacions = programacionRepositorio.findAll();
        return programacions.stream().map(programacion -> mapearDTO(programacion)).collect(Collectors.toList());
    }

    @Override
    public ProgramacionDTO crearProgramacion(ProgramacionDTO programacionDTO, Unidad unidad, Origen origen, Destino destino, List<Usuario> usuarios) {
        Programacion programacion = mapearEntidad(programacionDTO);
        programacion.setUnidad(unidad);
        programacion.setOrigen(origen);
        programacion.setDestino(destino);

        Programacion nuevaProgramacion = programacionRepositorio.save(programacion);
        tripulacionServicio.crearTripulacion(programacion, usuarios);
        unidadServicio.actualizarEstado(unidad, "Asignado");
        asientoProgramacionServicio.crearAsientosProgramacion(programacion, unidad);

        ProgramacionDTO programacionResponse = mapearDTO(nuevaProgramacion);
        return programacionResponse;
    }

    @Override
    public ProgramacionDTO getProgramacionById(int idProgramacion) {
        Programacion programacion = programacionRepositorio.findById(idProgramacion).get();
        return mapearDTO(programacion);
    }

    @Override
    public ProgramacionDTO modificarProgramacion(ProgramacionDTO programacionDTO, Unidad unidad, Origen origen, Destino destino, List<Usuario> usuarios, int idProgramacion) {
        Programacion oldProgramacion = programacionRepositorio.findById(idProgramacion).get();
        boolean sameUnidad = oldProgramacion.getUnidad().getIdUnidad() == unidad.getIdUnidad();
        unidadServicio.actualizarEstado(oldProgramacion.getUnidad(), "Activo");
        tripulacionServicio.eliminarTripulacionByProgramacion(oldProgramacion);

        Programacion programacion = mapearEntidad(programacionDTO);
        programacion.setIdProgramacion(idProgramacion);
        programacion.setUnidad(unidad);
        programacion.setOrigen(origen);
        programacion.setDestino(destino);

        Programacion programacionActualizada = programacionRepositorio.save(programacion);
        tripulacionServicio.crearTripulacion(programacion, usuarios);
        unidadServicio.actualizarEstado(unidad, "Asignado");
        asientoProgramacionServicio.actualizarAsientosProgramacion(programacion, unidad, sameUnidad);

        return mapearDTO(programacionActualizada);
    }

    @Override
    public void eliminarProgramacion(int idProgramacion) {
        Programacion programacion = programacionRepositorio.findById(idProgramacion).get();
        unidadServicio.actualizarEstado(programacion.getUnidad(), "Activo");
        tripulacionServicio.eliminarTripulacionByProgramacion(programacion);
        programacionRepositorio.delete(programacion);
    }

    @Override
    public List<ProgramacionDTO> obtenerProgramacionesByOrigenAndDestinoAndFecha(Origen origen, Destino destino, Date fechaIda) {
        List<Programacion> programacions = programacionRepositorio.findAllByOrigenAndDestinoAndFecha(origen,destino,fechaIda);
        return programacions.stream().map(programacion -> mapearDTO(programacion)).collect(Collectors.toList());
    }

    // Convierte entidad a DTO
    private ProgramacionDTO mapearDTO(Programacion programacion) {
        ProgramacionDTO programacionDTO = modelMapper.map(programacion, ProgramacionDTO.class);
        return programacionDTO;
    }
    // Convierte de DTO a Entidad
    private Programacion mapearEntidad(ProgramacionDTO programacionDTO) {
        Programacion programacion = modelMapper.map(programacionDTO, Programacion.class);
        return programacion;
    }

}
