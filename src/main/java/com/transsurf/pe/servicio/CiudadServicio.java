package com.transsurf.pe.servicio;

import com.transsurf.pe.dto.CiudadDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CiudadServicio {
    public CiudadDTO crearCiudad(CiudadDTO ciudadDTO);

    public CiudadDTO getCiudadById(int idCiudad);

    public CiudadDTO actualizarCiudad(CiudadDTO ciudadDTO, int idCiudad);

    public void elimarCiudad(int idCiudad);

    public List<CiudadDTO> obtenerCiudades();
}
