package com.transsurf.pe.controlador;

import com.transsurf.pe.dto.ReservaDTO;
import com.transsurf.pe.dto.UsuarioDTO;
import com.transsurf.pe.entidades.AsientoProgramacion;
import com.transsurf.pe.entidades.Programacion;
import com.transsurf.pe.entidades.Usuario;
import com.transsurf.pe.excepciones.ResourceNotFoundException;
import com.transsurf.pe.repositorio.AsientoProgramacionRepositorio;
import com.transsurf.pe.repositorio.ProgramacionRepositorio;
import com.transsurf.pe.servicio.ReservaServicio;
import com.transsurf.pe.servicio.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reserva")
public class ReservaControlador {

    @Autowired
    private ReservaServicio reservaServicio;
    @Autowired
    private UsuarioServicio usuarioServicio;
    @Autowired
    private AsientoProgramacionRepositorio asientoProgramacionRepositorio;
    @Autowired
    private ProgramacionRepositorio programacionRepositorio;

    @PreAuthorize("hasRole('ADMIN') or hasRole('CLIENT')")
    @GetMapping("/{idUsuario}")
    public List<ReservaDTO> listarReservasByUsuario(@PathVariable(name = "idUsuario") long idUsuario){
        Usuario usuario = usuarioServicio.getUsuarioById(idUsuario);

        return reservaServicio.listarReservasByUsuario(usuario);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('CLIENT')")
    @GetMapping("/getReserve")
    public ResponseEntity<?> obtenerReservaById(@RequestParam(name = "idReserva") Long idReserva) {
        return new ResponseEntity<>(reservaServicio.getReservaById(idReserva), HttpStatus.OK);
    }

    @GetMapping("/getReserveByAsientoProg")
    public ResponseEntity<?> obtenerReservaByasientoProgramacion(@RequestParam(name = "idAsientoProg") Long idAsientoProg) {
        AsientoProgramacion asientoProgramacion = asientoProgramacionRepositorio.findById(idAsientoProg).get();

        return new ResponseEntity<>(reservaServicio.getReservaByasientoProgramacion(asientoProgramacion), HttpStatus.OK);
    }

    @PostMapping("/{idAsientoProg}/{register}/{idDocumento}")
    public ResponseEntity<?> realizarReserva(@RequestBody UsuarioDTO usuarioDTO,
                                             @PathVariable(name = "idAsientoProg") long idAsientoProg,
                                             @PathVariable(name = "register") boolean register,
                                             @PathVariable(name = "idDocumento") int idDocumento) {
        AsientoProgramacion asientoProgramacion = asientoProgramacionRepositorio.findById(idAsientoProg)
                .orElseThrow(() -> new ResourceNotFoundException("AsientoProgramacion","idAsientoProg", idAsientoProg));

        Usuario usuario = usuarioServicio.findByDataOrRegister(usuarioDTO, idDocumento,register);

        return new ResponseEntity<>(reservaServicio.crearReserva(asientoProgramacion,usuario), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getList/{idProgramacion}")
    public List<ReservaDTO> listarReservasByProgramacion(@PathVariable(name = "idProgramacion") int idProgramacion) {
        Programacion programacion = programacionRepositorio.findById(idProgramacion).get();
        List<AsientoProgramacion> asientoProgramacions = asientoProgramacionRepositorio.findAllByProgramacion(programacion);

        return reservaServicio.listarReservasByAsientosProgramacions(asientoProgramacions);
    }
}
