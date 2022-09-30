package com.transsurf.pe.servicio;

import com.transsurf.pe.dto.MarcaDTO;

import java.util.List;

public interface MarcaServicio {
    public List<MarcaDTO> obtenerMarcas();

    public MarcaDTO guardarMarca(MarcaDTO marcaDTO);

    public MarcaDTO getMarcaById(int idMarca);

    public MarcaDTO actualizarMarca(MarcaDTO marcaDTO, int idMarca);

    public void elimarMarca(int idMarca);
}
