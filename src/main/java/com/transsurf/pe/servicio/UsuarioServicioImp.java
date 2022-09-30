package com.transsurf.pe.servicio;

import com.transsurf.pe.dto.UsuarioDTO;
import com.transsurf.pe.entidades.Documento;
import com.transsurf.pe.entidades.Rol;
import com.transsurf.pe.entidades.Usuario;
import com.transsurf.pe.excepciones.ResourceNotFoundException;
import com.transsurf.pe.repositorio.UsuarioRepositorio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioServicioImp implements UsuarioServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UsuarioDTO obtenerUsuarioByNumDocOrEmail(String numDocOrEmail) {
        Usuario usuario = usuarioRepositorio.findByNumDocOrEmail(numDocOrEmail, numDocOrEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario","numDocOrEmail",0));
        return mapearDTO(usuario);
    }

    @Override
    public List<UsuarioDTO> listarPersonal() {
        List<Usuario> usuarios = usuarioRepositorio.findAllByRolesNot(new Rol(2,"ROLE_CLIENT"));
        return usuarios.stream().map(usuario -> mapearDTO(usuario)).collect(Collectors.toList());
    }

    @Override
    public List<UsuarioDTO> listarClientes() {
        List<Usuario> usuarios = usuarioRepositorio.findAllByRolesIs(new Rol(2,"ROLE_CLIENT"));
        return usuarios.stream().map(usuario -> mapearDTO(usuario)).collect(Collectors.toList());
    }

    @Override
    public UsuarioDTO crearUsuarioPersonal(UsuarioDTO usuarioDTO, Documento documento, Rol rol) {
        Usuario usuario = mapearEntidad(usuarioDTO);
        usuario.setPassword(passwordEncoder.encode(usuarioDTO.getPassword()));
        usuario.setDocumento(documento);
        usuario.setRoles(Collections.singleton(rol));

        Usuario nuevoUsuario = usuarioRepositorio.save(usuario);

        UsuarioDTO usuarioResponse = mapearDTO(nuevoUsuario);
        return usuarioResponse;
    }

    @Override
    public UsuarioDTO obtenerUsuarioById(long idUsuario) {
        Usuario usuario = usuarioRepositorio.findById(idUsuario)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario","idUsuario",idUsuario));
        return mapearDTO(usuario);
    }

    @Override
    public UsuarioDTO modificarUsuarioPersonal(UsuarioDTO usuarioDTO, Documento documento, Rol rol, long idUsuario) {
        Usuario usuario = mapearEntidad(usuarioDTO);
        usuario.setPassword(passwordEncoder.encode(usuarioDTO.getPassword()));
        usuario.setDocumento(documento);
        usuario.setRoles(Collections.singleton(rol));
        usuario.setIdUsuario(idUsuario);

        Usuario usuarioActualizado = usuarioRepositorio.save(usuario);

        return mapearDTO(usuarioActualizado);
    }

    @Override
    public UsuarioDTO modificarUsuarioCliente(UsuarioDTO usuarioDTO, Documento documento, long idUsuario) {
        Usuario usuario = mapearEntidad(usuarioDTO);
        usuario.setPassword(passwordEncoder.encode(usuarioDTO.getPassword()));
        usuario.setDocumento(documento);
        usuario.setRoles(Collections.singleton(new Rol(2,"ROLE_CLIENT")));
        usuario.setIdUsuario(idUsuario);

        Usuario usuarioActualizado = usuarioRepositorio.save(usuario);

        return mapearDTO(usuarioActualizado);
    }

    @Override
    public void eliminarUsuario(long idUsuario) {
        Usuario usuario = usuarioRepositorio.findById(idUsuario).
                orElseThrow(() -> new ResourceNotFoundException("Usuario","idUsuario",idUsuario));

        usuarioRepositorio.delete(usuario);
    }

    //Mapear Entidad a DTO
    private UsuarioDTO mapearDTO(Usuario usuario) {
        UsuarioDTO usuarioDTO = modelMapper.map(usuario, UsuarioDTO.class);
        return usuarioDTO;
    }
    //Mapear DTO a Entidad
    private Usuario mapearEntidad(UsuarioDTO usuarioDTO) {
        Usuario usuario = modelMapper.map(usuarioDTO, Usuario.class);
        return usuario;
    }
}
