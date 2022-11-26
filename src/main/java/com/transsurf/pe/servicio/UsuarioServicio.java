package com.transsurf.pe.servicio;

import com.transsurf.pe.dto.UsuarioDTO;
import com.transsurf.pe.entidades.Documento;
import com.transsurf.pe.entidades.Rol;
import com.transsurf.pe.entidades.Usuario;

import java.util.List;
import java.util.Set;

public interface UsuarioServicio {

    public UsuarioDTO obtenerUsuarioByNumDocOrEmail(String numDocOrEmail);
    
    public List<UsuarioDTO> listarPersonal();

    public List<UsuarioDTO> listarClientes();

    public UsuarioDTO crearUsuarioPersonal(UsuarioDTO usuarioDTO, Documento documento, Set<Rol> roles);

    public UsuarioDTO obtenerUsuarioById(long idUsuario);

    public UsuarioDTO modificarUsuarioPersonal(UsuarioDTO usuarioDTO, Documento documento, Rol rol, long idUsuario);

    public UsuarioDTO modificarUsuarioCliente(UsuarioDTO usuarioDTO, Documento documento, long idUsuario);

    void eliminarUsuario(long idUsuario);

    public List<UsuarioDTO> listarOperators();

    public List<UsuarioDTO> listarDrivers();

    public void actualizarEstado(Usuario usuario, String estado);

    public UsuarioDTO modificarCliente(UsuarioDTO usuarioDTO, Documento documento, long idUsuario);

    public UsuarioDTO buscarClienteByDocumentoAndNumDoc(Documento documento, String numDoc);

    public Usuario findByDataOrRegister(UsuarioDTO usuarioDTO, int idDocumento, boolean register);

    public Usuario getUsuarioById(long idUsuario);
}
