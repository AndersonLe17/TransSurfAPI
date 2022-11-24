package com.transsurf.pe.servicio;

import com.transsurf.pe.dto.UnidadDTO;
import com.transsurf.pe.entidades.Marca;
import com.transsurf.pe.entidades.Modelo;
import com.transsurf.pe.entidades.Unidad;

import java.util.List;

public interface UnidadServicio {
    public List<UnidadDTO> obtenerUnidades();

    public UnidadDTO crearUnidad(UnidadDTO unidadDTO, Modelo modelo, Marca marca);

    public UnidadDTO getUnidadById(int idUnidad);

    public UnidadDTO modificarUnidad(UnidadDTO unidadDTO, Modelo modelo, Marca marca, int idUnidad);

    public void eliminarUnidad(int idUnidad);

    public void actualizarEstado(Unidad unidad, String estado);
}
