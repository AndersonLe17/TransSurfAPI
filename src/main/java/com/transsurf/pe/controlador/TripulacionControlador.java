package com.transsurf.pe.controlador;

import com.transsurf.pe.dto.TripulacionDTO;
import com.transsurf.pe.entidades.Programacion;
import com.transsurf.pe.entidades.Tripulacion;
import com.transsurf.pe.repositorio.ProgramacionRepositorio;
import com.transsurf.pe.servicio.TripulacionServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tripulacion")
public class TripulacionControlador {

    @Autowired
    private TripulacionServicio tripulacionServicio;

    @Autowired
    private ProgramacionRepositorio programacionRepositorio;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{idProgramacion}")
    public List<TripulacionDTO> listarTripulacionByProgramacion(@PathVariable(name = "idProgramacion") int idProgramacion) {
        Programacion programacion = programacionRepositorio.findById(idProgramacion).get();

        return tripulacionServicio.getTripuacionByProgramacion(programacion);
    }

}
