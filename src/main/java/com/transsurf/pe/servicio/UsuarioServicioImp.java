package com.transsurf.pe.servicio;

import com.transsurf.pe.dto.UsuarioDTO;
import com.transsurf.pe.entidades.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServicioImp implements UsuarioServicio {

    @Autowired
    private ModelMapper modelMapper;

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
