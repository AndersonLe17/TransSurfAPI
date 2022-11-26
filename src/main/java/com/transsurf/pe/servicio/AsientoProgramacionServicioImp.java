package com.transsurf.pe.servicio;

import com.transsurf.pe.dto.AsientoProgramacionDTO;
import com.transsurf.pe.entidades.*;
import com.transsurf.pe.repositorio.AsientoProgramacionRepositorio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AsientoProgramacionServicioImp implements AsientoProgramacionServicio{

    @Autowired
    private AsientoProgramacionRepositorio asientoProgramacionRepositorio;
    @Autowired
    private AsientoServicio asientoServicio;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public void crearAsientosProgramacion(Programacion programacion, Unidad unidad) {
        List<AsientoProgramacion> asientoProgramacions = new ArrayList<>();
        List<Asiento> asientos = asientoServicio.obtenerAsientosByUnidad(unidad);
        for (Asiento asiento: asientos) {
            AsientoProgramacion asientoProgramacion = new AsientoProgramacion();
            asientoProgramacion.setAsiento(asiento);
            asientoProgramacion.setEstado(asiento.getEstado());
            asientoProgramacion.setProgramacion(programacion);

            asientoProgramacions.add(asientoProgramacion);
        }
        asientoProgramacionRepositorio.saveAll(asientoProgramacions);
    }

    @Override
    public void actualizarAsientosProgramacion(Programacion programacion, Unidad unidad, boolean sameUnidad) {
        if (!sameUnidad) {
            List<AsientoProgramacion> asientoProgramacions = asientoProgramacionRepositorio.findAllByProgramacion(programacion);
            asientoProgramacionRepositorio.deleteAll(asientoProgramacions);
            crearAsientosProgramacion(programacion,unidad);
        }
    }

    @Override
    public List<AsientoProgramacionDTO> obtenerAsientosByProgramacion(Programacion programacion){
        List<AsientoProgramacion> asientoProgramacions = asientoProgramacionRepositorio.findAllByProgramacion(programacion);
        return asientoProgramacions.stream().map(asientoProgramacion -> mapearDTO(asientoProgramacion)).collect(Collectors.toList());
    }

    @Override
    public void actualizarEstadoAsiento(AsientoProgramacion asientoProgramacion, String estado) {
        asientoProgramacion.setEstado(estado);
        asientoProgramacionRepositorio.save(asientoProgramacion);
    }

    private AsientoProgramacionDTO mapearDTO(AsientoProgramacion asientoProgramacion) {
        AsientoProgramacionDTO asientoProgramacionDTO = modelMapper.map(asientoProgramacion, AsientoProgramacionDTO.class);
        return asientoProgramacionDTO;
    }
}
