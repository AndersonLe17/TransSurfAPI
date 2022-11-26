package com.transsurf.pe.controlador;

import com.transsurf.pe.dto.ReservaDTO;
import com.transsurf.pe.dto.UsuarioDTO;
import com.transsurf.pe.entidades.AsientoProgramacion;
import com.transsurf.pe.entidades.Usuario;
import com.transsurf.pe.excepciones.ResourceNotFoundException;
import com.transsurf.pe.repositorio.AsientoProgramacionRepositorio;
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

    @PreAuthorize("hasRole('ADMIN') or hasRole('CLIENT')")
    @GetMapping("/{idUsuario}")
    public List<ReservaDTO> listarReservasByUsuario(@PathVariable(name = "idUsuario") long idUsuario){
        Usuario usuario = usuarioServicio.getUsuarioById(idUsuario);

        return reservaServicio.listarReservasByUsuario(usuario);
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

}
