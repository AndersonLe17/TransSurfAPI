package com.transsurf.pe.controlador;

import com.transsurf.pe.dto.LoginDTO;
import com.transsurf.pe.dto.RegistroDTO;
import com.transsurf.pe.entidades.Documento;
import com.transsurf.pe.entidades.Rol;
import com.transsurf.pe.entidades.Usuario;
import com.transsurf.pe.excepciones.ResourceNotFoundException;
import com.transsurf.pe.repositorio.DocumentoRepositorio;
import com.transsurf.pe.repositorio.RolRepositorio;
import com.transsurf.pe.repositorio.UsuarioRepositorio;
import com.transsurf.pe.seguridad.JWTAuthResonseDTO;
import com.transsurf.pe.seguridad.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthControlador {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private RolRepositorio rolRepositorio;

    @Autowired
    private DocumentoRepositorio documentoRepositorio;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping(value = "/login")
    public ResponseEntity<JWTAuthResonseDTO> authenticateUser(@RequestBody LoginDTO loginDTO) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getNumDocOrEmail(),loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generarToken(authentication);
        return ResponseEntity.ok(new JWTAuthResonseDTO(token)); 
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> registrarCliente(@RequestBody RegistroDTO registroDTO) {
        System.out.println("Holas");
        if (usuarioRepositorio.existsByNumDoc(registroDTO.getNumDoc())) {
            return new ResponseEntity<>("Ese numero de documento ya existe", HttpStatus.BAD_REQUEST);
        } else if (usuarioRepositorio.existsByEmail(registroDTO.getEmail())) {
            return new ResponseEntity<>("Ese email ya existe", HttpStatus.BAD_REQUEST);
        }

        Documento documento = documentoRepositorio.findById(registroDTO.getIdDocumento())
                .orElseThrow(() -> new ResourceNotFoundException("Documento", "id", registroDTO.getIdDocumento()));

        Usuario usuario = new Usuario();
        usuario.setDocumento(documento);
        usuario.setNumDoc(registroDTO.getNumDoc());
        usuario.setNombre(registroDTO.getNombre());
        usuario.setApellido(registroDTO.getApellido());
        usuario.setFechaNacimiento(registroDTO.getFechaNacimiento());
        usuario.setEmail(registroDTO.getEmail());
        usuario.setPassword(passwordEncoder.encode(registroDTO.getPassword()));
        usuario.setCelular(registroDTO.getCelular());
        usuario.setEstado(registroDTO.getEstado());

        Rol rol = rolRepositorio.findByTipo("ROLE_CLIENT").get();

        usuario.setRoles(Collections.singleton(rol));

        usuarioRepositorio.save(usuario);

        return new ResponseEntity<>("Usuario registrado exitosamente",HttpStatus.OK);
    }

}
