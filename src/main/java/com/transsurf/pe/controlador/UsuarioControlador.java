package com.transsurf.pe.controlador;

import com.transsurf.pe.dto.DocumentoDTO;
import com.transsurf.pe.dto.RolDTO;
import com.transsurf.pe.dto.UsuarioDTO;
import com.transsurf.pe.entidades.Documento;
import com.transsurf.pe.entidades.Rol;
import com.transsurf.pe.entidades.Usuario;
import com.transsurf.pe.excepciones.AppException;
import com.transsurf.pe.excepciones.ResourceNotFoundException;
import com.transsurf.pe.repositorio.DocumentoRepositorio;
import com.transsurf.pe.repositorio.RolRepositorio;
import com.transsurf.pe.repositorio.UsuarioRepositorio;
import com.transsurf.pe.servicio.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioControlador {
    @Autowired
    private UsuarioServicio usuarioServicio;
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private RolRepositorio rolRepositorio;
    @Autowired
    private DocumentoRepositorio documentoRepositorio;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PreAuthorize("hasRole('ADMIN') or hasRole('CLIENT')")
    @PostMapping
    public ResponseEntity<UsuarioDTO> getUserData(@RequestBody String numDocOrEmail) {
        return ResponseEntity.ok(usuarioServicio.obtenerUsuarioByNumDocOrEmail(numDocOrEmail));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/personal")
    public List<UsuarioDTO> getUsuariosPersonal() {
        return usuarioServicio.listarPersonal();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/cliente")
    public List<UsuarioDTO> getUsuariosCliente() {
        return usuarioServicio.listarClientes();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/driver")
    public List<UsuarioDTO> getDrivers() {
        return usuarioServicio.listarDrivers();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/operator")
    public List<UsuarioDTO> getOperators() {
        return usuarioServicio.listarOperators();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/personal/{idDocumento}/{idRol}")
    public ResponseEntity<?> guardarPersonal(@RequestBody UsuarioDTO usuarioDTO,
                                             @PathVariable(name = "idDocumento") int idDocumento,
                                             @PathVariable(name = "idRol") int idRol){
        if (usuarioRepositorio.existsByNumDoc(usuarioDTO.getNumDoc())) {
            return new ResponseEntity<>("Documento Existente", HttpStatus.BAD_REQUEST);
        }

        Documento documento = documentoRepositorio.findById(idDocumento)
                .orElseThrow(() -> new ResourceNotFoundException("Documento","id",idDocumento));

        Rol rol1 = rolRepositorio.findById(idRol)
                .orElseThrow(() -> new ResourceNotFoundException("Rol","id",idRol));
        Rol rol2 = rolRepositorio.findById(2).get();
        Set<Rol> roles = new HashSet<>();
        roles.add(rol1);
        roles.add(rol2);

        return new ResponseEntity<>(usuarioServicio.crearUsuarioPersonal(usuarioDTO, documento, roles), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{idUsuario}")
    public UsuarioDTO getUsuario(@PathVariable(name = "idUsuario") long idUsuario) {
        return usuarioServicio.obtenerUsuarioById(idUsuario);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/personal/{idDocumento}/{idRol}/{idUsuario}")
    public ResponseEntity<?> editPersonal(@RequestBody UsuarioDTO usuarioDTO,
                                          @PathVariable(name = "idDocumento") int idDocumento,
                                          @PathVariable(name = "idRol") int idRol,
                                          @PathVariable(name = "idUsuario") long idUsuario){
        if (usuarioRepositorio.existsByNumDoc(usuarioDTO.getNumDoc())) {
            Usuario usuario = usuarioRepositorio.findById(idUsuario)
                    .orElseThrow(() -> new ResourceNotFoundException("Usuario","idUsuario",idUsuario));
            if (!usuarioDTO.getNumDoc().equals(usuario.getNumDoc())) {
                return new ResponseEntity<>("Documento existente.", HttpStatus.BAD_REQUEST);
            }
        } else if (!usuarioRepositorio.existsById(idUsuario)) {
            return new ResponseEntity<>("El idUsuario no existe", HttpStatus.BAD_REQUEST);
        }

        Documento documento = documentoRepositorio.findById(idDocumento)
                .orElseThrow(() -> new ResourceNotFoundException("Documento","id",idDocumento));

        Rol rol = rolRepositorio.findById(idRol)
                .orElseThrow(() -> new ResourceNotFoundException("Rol","id",idRol));

        return new ResponseEntity<>(usuarioServicio.modificarUsuarioPersonal(usuarioDTO, documento, rol, idUsuario), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/cliente/{idDocumento}/{idUsuario}")
    public ResponseEntity<?> editCliente(@RequestBody UsuarioDTO usuarioDTO,
                                          @PathVariable(name = "idDocumento") int idDocumento,
                                          @PathVariable(name = "idUsuario") long idUsuario){
        if (usuarioRepositorio.existsByNumDoc(usuarioDTO.getNumDoc())) {
            Usuario usuario = usuarioRepositorio.findById(idUsuario)
                    .orElseThrow(() -> new ResourceNotFoundException("Usuario","idUsuario",idUsuario));
            if (!usuarioDTO.getNumDoc().equals(usuario.getNumDoc())) {
                return new ResponseEntity<>("Ese numero de documento ya existe", HttpStatus.BAD_REQUEST);
            }
        } else if (!usuarioRepositorio.existsById(idUsuario)) {
            return new ResponseEntity<>("El idUsuario no existe", HttpStatus.BAD_REQUEST);
        }

        Documento documento = documentoRepositorio.findById(idDocumento)
                .orElseThrow(() -> new ResourceNotFoundException("Documento","id",idDocumento));

        return new ResponseEntity<>(usuarioServicio.modificarUsuarioCliente(usuarioDTO, documento, idUsuario), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('CLIENT')")
    @PutMapping("client/{idDocumento}/{idUsuario}")
    public ResponseEntity<?> editClient(@RequestBody UsuarioDTO usuarioDTO,
                                        @PathVariable(name = "idDocumento") int idDocumento,
                                        @PathVariable(name = "idUsuario") long idUsuario){
        if (usuarioRepositorio.existsByNumDoc(usuarioDTO.getNumDoc())) {
            Usuario usuario = usuarioRepositorio.findById(idUsuario)
                    .orElseThrow(() -> new ResourceNotFoundException("Usuario","idUsuario",idUsuario));
            if (!usuarioDTO.getNumDoc().equals(usuario.getNumDoc())) {
                return new ResponseEntity<>("Ese numero de documento ya existe", HttpStatus.BAD_REQUEST);
            }
        } else if (!usuarioRepositorio.existsById(idUsuario)) {
            return new ResponseEntity<>("El idUsuario no existe", HttpStatus.BAD_REQUEST);
        }

        Documento documento = documentoRepositorio.findById(idDocumento)
                .orElseThrow(() -> new ResourceNotFoundException("Documento","id",idDocumento));

        return new ResponseEntity<>(usuarioServicio.modificarCliente(usuarioDTO,documento,idUsuario), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('CLIENT')")
    @PutMapping("{passActual}/{idUsuario}")
    public ResponseEntity<?> editClientPassword(@RequestBody UsuarioDTO usuarioDTO,
                                                @PathVariable(name = "idUsuario") long idUsuario,
                                                @PathVariable(name = "passActual") String passActual){
        Usuario usuario = usuarioRepositorio.findById(idUsuario).get();

        if (!passwordEncoder.matches(passActual, usuario.getPassword())) {
            throw new AppException(HttpStatus.BAD_REQUEST,"La contraseña Actual no coincide");
        }

        return new ResponseEntity<>(usuarioServicio.modificarCliente(usuarioDTO,null,idUsuario), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{idUsuario}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable(name = "idUsuario") long idUsuario) {
        usuarioServicio.eliminarUsuario(idUsuario);

        return new ResponseEntity<>("Usuario Eliminado con exito",HttpStatus.OK);
    }

    @GetMapping("/{idDocumento}/{numDoc}")
    public ResponseEntity<?> buscarClienteByDocumentoAndNumDoc(@PathVariable(name = "idDocumento") int idDocumento, @PathVariable(name = "numDoc") String numDoc) {
        Documento documento = documentoRepositorio.findById(idDocumento)
                .orElseThrow(() -> new ResourceNotFoundException("Documento","id",idDocumento));

        UsuarioDTO usuarioDTO = usuarioServicio.buscarClienteByDocumentoAndNumDoc(documento,numDoc);
        return new ResponseEntity<>(usuarioDTO,HttpStatus.OK);
    }
}
