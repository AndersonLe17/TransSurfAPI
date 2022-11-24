package com.transsurf.pe.servicio;

import com.transsurf.pe.dto.UnidadDTO;
import com.transsurf.pe.entidades.Marca;
import com.transsurf.pe.entidades.Modelo;
import com.transsurf.pe.entidades.Unidad;
import com.transsurf.pe.excepciones.ResourceNotFoundException;
import com.transsurf.pe.repositorio.UnidadRepositorio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UnidadServicioImp implements UnidadServicio{

    @Autowired
    private UnidadRepositorio unidadRepositorio;

    @Autowired
    private AsientoServicio asientoServicio;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<UnidadDTO> obtenerUnidades() {
        List<Unidad> unidades = unidadRepositorio.findAll();
        return unidades.stream().map(unidad -> mapearDTO(unidad)).collect(Collectors.toList());
    }

    @Override
    public UnidadDTO crearUnidad(UnidadDTO unidadDTO, Modelo modelo, Marca marca) {
        Unidad unidad = mapearEntidad(unidadDTO);
        unidad.setModelo(modelo);
        unidad.setMarca(marca);

        Unidad nuevaUnidad = unidadRepositorio.save(unidad);
        asientoServicio.crearAsientos(unidad);

        UnidadDTO unidadResponse = mapearDTO(nuevaUnidad);
        return unidadResponse;
    }

    @Override
    public UnidadDTO getUnidadById(int idUnidad) {
        Unidad unidad = unidadRepositorio.findById(idUnidad).
                orElseThrow(() -> new ResourceNotFoundException("Unidad","id",idUnidad));
        return mapearDTO(unidad);
    }

    @Override
    public UnidadDTO modificarUnidad(UnidadDTO unidadDTO, Modelo modelo, Marca marca, int idUnidad) {
        Unidad unidad = mapearEntidad(unidadDTO);
        unidad.setModelo(modelo);
        unidad.setMarca(marca);
        unidad.setIdUnidad(idUnidad);

        Unidad unidadActualizada = unidadRepositorio.save(unidad);
        asientoServicio.actualizarAsientos(unidad);

        return mapearDTO(unidadActualizada);
    }

    @Override
    public void eliminarUnidad(int idUnidad) {
        Unidad unidad = unidadRepositorio.findById(idUnidad).get();

        unidadRepositorio.delete(unidad);
    }

    @Override
    public void actualizarEstado(Unidad unidad, String estado) {
        unidad.setEstado(estado);

        unidadRepositorio.save(unidad);
    }


    //Mapear Entidad a DTO
    private UnidadDTO mapearDTO(Unidad unidad) {
        UnidadDTO unidadDTO = modelMapper.map(unidad, UnidadDTO.class);
        return unidadDTO;
    }
    //Mapear DTO a Entidad
    private Unidad mapearEntidad(UnidadDTO unidadDTO) {
        Unidad unidad = modelMapper.map(unidadDTO, Unidad.class);
        return unidad;
    }
}
