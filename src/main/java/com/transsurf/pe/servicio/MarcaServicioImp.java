package com.transsurf.pe.servicio;

import com.transsurf.pe.dto.MarcaDTO;
import com.transsurf.pe.entidades.Marca;
import com.transsurf.pe.excepciones.ResourceNotFoundException;
import com.transsurf.pe.repositorio.MarcaRepositorio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MarcaServicioImp implements MarcaServicio {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MarcaRepositorio marcaRepositorio;

    @Override
    public List<MarcaDTO> obtenerMarcas() {
        List<Marca> marcas = marcaRepositorio.findAll();

        return marcas.stream().map(marca -> mapearDTO(marca)).collect(Collectors.toList());
    }

    @Override
    public MarcaDTO guardarMarca(MarcaDTO marcaDTO) {
        Marca marca = mapearEntidad(marcaDTO);

        Marca nuevaMarca = marcaRepositorio.save(marca);

        MarcaDTO marcaResponse = mapearDTO(nuevaMarca);
        return marcaResponse;
    }

    @Override
    public MarcaDTO getMarcaById(int idMarca) {
        Marca marca = marcaRepositorio.findById(idMarca).
                orElseThrow(() -> new ResourceNotFoundException("Marca", "idMarca", idMarca));

        return mapearDTO(marca);
    }

    @Override
    public MarcaDTO actualizarMarca(MarcaDTO marcaDTO, int idMarca) {
        Marca marca = marcaRepositorio.findById(idMarca).
                orElseThrow(() -> new ResourceNotFoundException("Marca", "idMarca", idMarca));

        marca.setNombre(marcaDTO.getNombre());
        marca.setEstado(marcaDTO.getEstado());

        Marca marcaActualizado = marcaRepositorio.save(marca);

        return mapearDTO(marcaActualizado);
    }

    @Override
    public void elimarMarca(int idMarca) {
        Marca marca = marcaRepositorio.findById(idMarca).
                orElseThrow(() -> new ResourceNotFoundException("Marca", "idMarca", idMarca));

        marcaRepositorio.delete(marca);
    }

    // Convierte entidad a DTO
    private MarcaDTO mapearDTO(Marca marca) {
        MarcaDTO marcaDTO = modelMapper.map(marca, MarcaDTO.class);
        return marcaDTO;
    }
    // Convierte de DTO a Entidad
    private Marca mapearEntidad(MarcaDTO marcaDTO) {
        Marca marca = modelMapper.map(marcaDTO, Marca.class);
        return marca;
    }

}
