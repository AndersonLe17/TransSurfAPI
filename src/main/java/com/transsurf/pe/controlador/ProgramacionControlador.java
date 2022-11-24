package com.transsurf.pe.controlador;

import com.transsurf.pe.dto.ProgramacionDTO;
import com.transsurf.pe.entidades.Destino;
import com.transsurf.pe.entidades.Origen;
import com.transsurf.pe.entidades.Unidad;
import com.transsurf.pe.entidades.Usuario;
import com.transsurf.pe.excepciones.ResourceNotFoundException;
import com.transsurf.pe.repositorio.DestinoRepositorio;
import com.transsurf.pe.repositorio.OrigenRepositorio;
import com.transsurf.pe.repositorio.UnidadRepositorio;
import com.transsurf.pe.repositorio.UsuarioRepositorio;
import com.transsurf.pe.servicio.ProgramacionServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/programacion")
public class ProgramacionControlador {

    @Autowired
    private ProgramacionServicio programacionServicio;

    @Autowired
    private UnidadRepositorio unidadRepositorio;

    @Autowired
    private OrigenRepositorio origenRepositorio;

    @Autowired
    private DestinoRepositorio destinoRepositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<ProgramacionDTO> listarProgramaciones() {
        return programacionServicio.obtenerProgramaciones();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{idUnidad}/{idOrigen}/{idDestino}/{idUsuarios}")
    public ResponseEntity<?> guardarProgramacion(@RequestBody ProgramacionDTO programacionDTO, @PathVariable(name = "idUnidad") int idUnidad,
                                                 @PathVariable(name = "idOrigen") Long idOrigen, @PathVariable(name = "idDestino") Long idDestino,
                                                 @PathVariable(name = "idUsuarios") ArrayList<Long> idUsuarios){
        Unidad unidad = unidadRepositorio.findById(idUnidad).
                orElseThrow(() -> new ResourceNotFoundException("Unidad","id",idUnidad));
        Origen origen = origenRepositorio.findById(idOrigen).
                orElseThrow(() -> new ResourceNotFoundException("Origen","id",idOrigen));
        Destino destino = destinoRepositorio.findById(idDestino).
                orElseThrow(() -> new ResourceNotFoundException("Destino","id",idDestino));
        List<Usuario> usuarios = usuarioRepositorio.findAllByIdUsuarioIn(idUsuarios);

        return new ResponseEntity<>(programacionServicio.crearProgramacion(programacionDTO,unidad, origen, destino, usuarios), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{idProgramacion}")
    public ResponseEntity<ProgramacionDTO> obtenerProgramacionById(@PathVariable(name = "idProgramacion") int idProgramacion) {
        return new ResponseEntity<>(programacionServicio.getProgramacionById(idProgramacion), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{idProgramacion}/{idUnidad}/{idOrigen}/{idDestino}/{idUsuarios}")
    public ResponseEntity<?> editarProgramacion(@RequestBody ProgramacionDTO programacionDTO, @PathVariable(name = "idProgramacion") int idProgramacion,
                                                 @PathVariable(name = "idUnidad") int idUnidad, @PathVariable(name = "idOrigen") Long idOrigen,
                                                 @PathVariable(name = "idDestino") Long idDestino, @PathVariable(name = "idUsuarios") ArrayList<Long> idUsuarios){
        Unidad unidad = unidadRepositorio.findById(idUnidad).
                orElseThrow(() -> new ResourceNotFoundException("Unidad","id",idUnidad));
        Origen origen = origenRepositorio.findById(idOrigen).
                orElseThrow(() -> new ResourceNotFoundException("Origen","id",idOrigen));
        Destino destino = destinoRepositorio.findById(idDestino).
                orElseThrow(() -> new ResourceNotFoundException("Destino","id",idDestino));
        List<Usuario> usuarios = usuarioRepositorio.findAllByIdUsuarioIn(idUsuarios);

        return new ResponseEntity<>(programacionServicio.modificarProgramacion(programacionDTO, unidad, origen, destino, usuarios, idProgramacion) ,HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{idProgramacion}")
    public ResponseEntity<String> eliminarProgramacion(@PathVariable(name = "idProgramacion") int idProgramacion) {
        programacionServicio.eliminarProgramacion(idProgramacion);

        return new ResponseEntity<>("Programacion Eliminada con exito",HttpStatus.OK);
    }

    @GetMapping("/{codOrigen}/{codDestino}/{fechaIda}")
    public ResponseEntity<?> listarBusqueda(@PathVariable(name = "codOrigen") Long codOrigen,
                                            @PathVariable(name = "codDestino") Long codDestino,
                                            @PathVariable(name = "fechaIda") Date fechaIda) {
        Origen origen = origenRepositorio.findById(codOrigen)
                .orElseThrow(() -> new ResourceNotFoundException("Origen", "id", codOrigen));
        Destino destino = destinoRepositorio.findById(codDestino).
                orElseThrow(() -> new ResourceNotFoundException("Destino","id",codDestino));

        return new ResponseEntity<>(programacionServicio.obtenerProgramacionesByOrigenAndDestinoAndFecha(origen, destino, fechaIda),HttpStatus.OK);
    }

}
