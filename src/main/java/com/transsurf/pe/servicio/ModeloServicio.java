package com.transsurf.pe.servicio;

import com.transsurf.pe.dto.ModeloDTO;

import java.util.List;

public interface ModeloServicio {
    public List<ModeloDTO> obtenerModelo();

    public ModeloDTO guardarModelo(ModeloDTO modeloDTO);

    public ModeloDTO getModeloById(int idModelo);

    public ModeloDTO actualizarModelo(ModeloDTO modeloDTO, int idModelo);

    public void elimarModelo(int idModelo);
}
