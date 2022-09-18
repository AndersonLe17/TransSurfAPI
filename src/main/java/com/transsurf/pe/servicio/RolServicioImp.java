package com.transsurf.pe.servicio;

import com.transsurf.pe.dto.RolDTO;
import com.transsurf.pe.entidades.Rol;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolServicioImp implements RolServicio {

    @Autowired
    private ModelMapper modelMapper;

    // Convierte entidad a DTO
    private RolDTO mapearDTO(Rol rol) {
        RolDTO rolDTO = modelMapper.map(rol, RolDTO.class);
        return rolDTO;
    }
    // Convierte de DTO a Entidad
    private Rol mapearEntidad(RolDTO rolDTO) {
        Rol rol = modelMapper.map(rolDTO, Rol.class);
        return rol;
    }
}
