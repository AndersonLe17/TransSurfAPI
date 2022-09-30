package com.transsurf.pe.servicio;

import com.transsurf.pe.dto.UsuarioDTO;
import com.transsurf.pe.entidades.Documento;
import com.transsurf.pe.entidades.Rol;

import java.util.List;

public interface UsuarioServicio {

    public UsuarioDTO obtenerUsuarioByNumDocOrEmail(String numDocOrEmail);
    
    public List<UsuarioDTO> listarPersonal();

    public List<UsuarioDTO> listarClientes();

    public UsuarioDTO crearUsuarioPersonal(UsuarioDTO usuarioDTO, Documento documento, Rol rol);

    public UsuarioDTO obtenerUsuarioById(long idUsuario);

    public UsuarioDTO modificarUsuarioPersonal(UsuarioDTO usuarioDTO, Documento documento, Rol rol, long idUsuario);

    public UsuarioDTO modificarUsuarioCliente(UsuarioDTO usuarioDTO, Documento documento, long idUsuario);

    void eliminarUsuario(long idUsuario);

}
