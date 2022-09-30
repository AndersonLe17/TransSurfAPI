package com.transsurf.pe.servicio;

import com.transsurf.pe.dto.ModeloDTO;
import com.transsurf.pe.entidades.Modelo;
import com.transsurf.pe.excepciones.ResourceNotFoundException;
import com.transsurf.pe.repositorio.ModeloRepositorio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ModeloServicioImp implements ModeloServicio {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ModeloRepositorio modeloRepositorio;

    @Override
    public List<ModeloDTO> obtenerModelo() {
        List<Modelo> modelos = modeloRepositorio.findAll();

        return modelos.stream().map(modelo -> mapearDTO(modelo)).collect(Collectors.toList());
    }

    @Override
    public ModeloDTO guardarModelo(ModeloDTO modeloDTO) {
        Modelo modelo = mapearEntidad(modeloDTO);

        Modelo nuevoModelo = modeloRepositorio.save(modelo);

        ModeloDTO modeloResponse = mapearDTO(nuevoModelo);
        return null;
    }

    @Override
    public ModeloDTO getModeloById(int idModelo) {
        Modelo modelo = modeloRepositorio.findById(idModelo).
                orElseThrow(() -> new ResourceNotFoundException("Modelo","idModelo",idModelo));

        return mapearDTO(modelo);
    }

    @Override
    public ModeloDTO actualizarModelo(ModeloDTO modeloDTO, int idModelo) {
        Modelo modelo = modeloRepositorio.findById(idModelo).
                orElseThrow(() -> new ResourceNotFoundException("Modelo","idModelo",idModelo));

        modelo.setNombre(modeloDTO.getNombre());
        modelo.setDescripcion(modeloDTO.getDescripcion());
        modelo.setEstado(modeloDTO.getEstado());

        Modelo modeloActualizado = modeloRepositorio.save(modelo);

        return mapearDTO(modeloActualizado);
    }

    @Override
    public void elimarModelo(int idModelo) {
        Modelo modelo = modeloRepositorio.findById(idModelo).
                orElseThrow(() -> new ResourceNotFoundException("Modelo","idModelo",idModelo));

        modeloRepositorio.delete(modelo);
    }

    // Convierte entidad a DTO
    private ModeloDTO mapearDTO(Modelo modelo) {
        ModeloDTO modeloDTO = modelMapper.map(modelo, ModeloDTO.class);
        return modeloDTO;
    }
    // Convierte de DTO a Entidad
    private Modelo mapearEntidad(ModeloDTO modeloDTO) {
        Modelo modelo = modelMapper.map(modeloDTO, Modelo.class);
        return modelo;
    }

}
