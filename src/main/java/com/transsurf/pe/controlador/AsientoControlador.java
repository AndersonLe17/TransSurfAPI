package com.transsurf.pe.controlador;

import com.transsurf.pe.dto.AsientoProgramacionDTO;
import com.transsurf.pe.entidades.Asiento;
import com.transsurf.pe.entidades.AsientoProgramacion;
import com.transsurf.pe.entidades.Programacion;
import com.transsurf.pe.entidades.Unidad;
import com.transsurf.pe.excepciones.ResourceNotFoundException;
import com.transsurf.pe.repositorio.ProgramacionRepositorio;
import com.transsurf.pe.repositorio.UnidadRepositorio;
import com.transsurf.pe.servicio.AsientoProgramacionServicio;
import com.transsurf.pe.servicio.AsientoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/asiento")
public class AsientoControlador {

    @Autowired
    private AsientoProgramacionServicio asientoProgramacionServicio;

    @Autowired
    private ProgramacionRepositorio programacionRepositorio;

    @GetMapping("/{idProgramacion}")
    public List<AsientoProgramacionDTO> listarAsientosProgramacionByProgramacion(@PathVariable(name = "idProgramacion") int idProgramacion) {
        Programacion programacion = programacionRepositorio.findById(idProgramacion)
                .orElseThrow(() -> new ResourceNotFoundException("idProgramacion", "id", idProgramacion));
        return asientoProgramacionServicio.obtenerAsientosByProgramacion(programacion);
    }
}
