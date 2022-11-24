package com.transsurf.pe.servicio;

import com.transsurf.pe.dto.CiudadDTO;
import com.transsurf.pe.entidades.Ciudad;
import com.transsurf.pe.entidades.Destino;
import com.transsurf.pe.entidades.Origen;
import com.transsurf.pe.excepciones.ResourceNotFoundException;
import com.transsurf.pe.repositorio.CiudadRepositorio;
import com.transsurf.pe.repositorio.DestinoRepositorio;
import com.transsurf.pe.repositorio.OrigenRepositorio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CiudadServicionImp implements CiudadServicio{

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CiudadRepositorio ciudadRepositorio;

    @Autowired
    private OrigenRepositorio origenRepositorio;

    @Autowired
    private DestinoRepositorio destinoRepositorio;

    @Override
    public CiudadDTO crearCiudad(CiudadDTO ciudadDTO) {
        Ciudad ciudad = mapearEntidad(ciudadDTO);

        Ciudad nuevaCiudad = ciudadRepositorio.save(ciudad);

        origenRepositorio.save(new Origen(nuevaCiudad));
        destinoRepositorio.save(new Destino(nuevaCiudad));

        CiudadDTO ciudadResponse = mapearDTO(nuevaCiudad);
        return ciudadResponse;
    }

    @Override
    public CiudadDTO getCiudadById(int idCiudad) {
        Ciudad ciudad = ciudadRepositorio.findById(idCiudad).
                orElseThrow(() -> new ResourceNotFoundException("Ciudad","idCiudad",idCiudad));
        return mapearDTO(ciudad);
    }

    @Override
    public CiudadDTO actualizarCiudad(CiudadDTO ciudadDTO, int idCiudad) {
        Ciudad ciudad = mapearEntidad(ciudadDTO);
        ciudad.setIdCiudad(idCiudad);

        Ciudad ciudadActualizado = ciudadRepositorio.save(ciudad);

        return mapearDTO(ciudadActualizado);
    }

    @Override
    public void elimarCiudad(int idCiudad) {
        Ciudad ciudad = ciudadRepositorio.findById(idCiudad).
                orElseThrow(() -> new ResourceNotFoundException("Ciudad","idCiudad",idCiudad));

        ciudadRepositorio.delete(ciudad);
    }

    @Override
    public List<CiudadDTO> obtenerCiudades() {
        List<Ciudad> ciudades = ciudadRepositorio.findAll();
        return ciudades.stream().map(ciudad -> mapearDTO(ciudad)).collect(Collectors.toList());
    }

    // Convierte entidad a DTO
    private CiudadDTO mapearDTO(Ciudad ciudad) {
        CiudadDTO ciudadDTO = modelMapper.map(ciudad, CiudadDTO.class);
        return ciudadDTO;
    }
    // Convierte de DTO a Entidad
    private Ciudad mapearEntidad(CiudadDTO ciudadDTO) {
        Ciudad ciudad = modelMapper.map(ciudadDTO, Ciudad.class);
        return ciudad;
    }

}
