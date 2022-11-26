package com.transsurf.pe.servicio;

import com.transsurf.pe.dto.UsuarioDTO;
import com.transsurf.pe.entidades.Documento;
import com.transsurf.pe.entidades.Rol;
import com.transsurf.pe.entidades.Usuario;
import com.transsurf.pe.excepciones.AppException;
import com.transsurf.pe.excepciones.ResourceNotFoundException;
import com.transsurf.pe.repositorio.DocumentoRepositorio;
import com.transsurf.pe.repositorio.UsuarioRepositorio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UsuarioServicioImp implements UsuarioServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private DocumentoRepositorio documentoRepositorio;

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
    public UsuarioDTO crearUsuarioPersonal(UsuarioDTO usuarioDTO, Documento documento, Set<Rol> roles) {
        Usuario usuario = mapearEntidad(usuarioDTO);
        usuario.setPassword(passwordEncoder.encode(usuarioDTO.getPassword()));
        usuario.setDocumento(documento);
        usuario.setRoles(roles);

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
        usuario.setRoles(Collections.singleton(new Rol(2)));
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

    @Override
    public List<UsuarioDTO> listarOperators() {
        List<Usuario> usuarios = usuarioRepositorio.findAllByRolesIs(new Rol(5));
        return usuarios.stream().map(usuario -> mapearDTO(usuario)).collect(Collectors.toList());
    }

    @Override
    public List<UsuarioDTO> listarDrivers() {
        List<Usuario> usuarios = usuarioRepositorio.findAllByRolesIs(new Rol(4));
        return usuarios.stream().map(usuario -> mapearDTO(usuario)).collect(Collectors.toList());
    }

    @Override
    public void actualizarEstado(Usuario usuario, String estado) {
        usuario.setEstado(estado);

        usuarioRepositorio.save(usuario);
    }

    @Override
    public UsuarioDTO modificarCliente(UsuarioDTO usuarioDTO, Documento documento, long idUsuario) {
        Usuario usuario = usuarioRepositorio.findById(idUsuario).get();
        usuario.setNombre((usuarioDTO.getNombre() == null)? usuario.getNombre() : usuarioDTO.getNombre());
        usuario.setApellido((usuarioDTO.getApellido() == null)? usuario.getApellido() : usuarioDTO.getApellido());
        usuario.setDocumento((documento == null)? usuario.getDocumento() : documento);
        usuario.setNumDoc((usuarioDTO.getNumDoc() == null)? usuario.getNumDoc() : usuarioDTO.getNumDoc());
        usuario.setCelular((usuarioDTO.getCelular() == null)? usuario.getCelular() : usuarioDTO.getCelular());
        usuario.setFechaNacimiento((usuarioDTO.getFechaNacimiento() == null)? usuario.getFechaNacimiento() : usuarioDTO.getFechaNacimiento());

        usuario.setPassword((usuarioDTO.getPassword() == null)? usuario.getPassword() : passwordEncoder.encode(usuarioDTO.getPassword()));

        Usuario usuarioActualizado = usuarioRepositorio.save(usuario);

        return mapearDTO(usuarioActualizado);
    }

    @Override
    public UsuarioDTO buscarClienteByDocumentoAndNumDoc(Documento documento, String numDoc) {
        Usuario usuario = usuarioRepositorio.findByDocumentoAndNumDoc(documento, numDoc)
                .orElseThrow(() -> new AppException(HttpStatus.BAD_REQUEST,"No hay resultado"));

        return mapearDTO(usuario);
    }

    @Override
    public Usuario findByDataOrRegister(UsuarioDTO usuarioDTO, int idDocumento, boolean register) {
        Documento documento = documentoRepositorio.findById(idDocumento).get();
        Usuario usuario;
        if (register) {
            Usuario usuarioBusqueda = usuarioRepositorio.findByDocumentoAndNumDoc(documento,usuarioDTO.getNumDoc())
                    .orElseThrow(() -> new AppException(HttpStatus.BAD_REQUEST,"El documento y el numero de documento no pertenecen a un cliente"));
            usuario = mapearEntidad(modificarCliente(usuarioDTO, null, usuarioBusqueda.getIdUsuario()));
        } else {
            usuario = mapearEntidad(usuarioDTO);
            usuario.setEmail(usuarioRepositorio.existsByEmail(usuarioDTO.getEmail())? null:usuarioDTO.getEmail());
            usuario.setDocumento(documento);
            usuario.setEstado("No Registrado");

            usuario = usuarioRepositorio.save(usuario);
        }
        return usuario;
    }

    @Override
    public Usuario getUsuarioById(long idUsuario) {
        return usuarioRepositorio.findById(idUsuario).get();
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
