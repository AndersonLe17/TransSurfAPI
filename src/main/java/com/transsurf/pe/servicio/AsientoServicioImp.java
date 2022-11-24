package com.transsurf.pe.servicio;

import com.transsurf.pe.entidades.Asiento;
import com.transsurf.pe.entidades.Nivel;
import com.transsurf.pe.entidades.Unidad;
import com.transsurf.pe.repositorio.AsientoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AsientoServicioImp implements AsientoServicio{

    @Autowired
    private AsientoRepositorio asientoRepositorio;

    @Override
    public void crearAsientos(Unidad unidad) {
        List<Asiento> asientos = new ArrayList<>();
        for (int i = 1; i <= unidad.getNumAsientos(); i++) {
            Asiento asiento = new Asiento();
            asiento.setUnidad(unidad);
            asiento.setNivel(new Nivel((i <= 12)? 1 : 2, ""));
            asiento.setNumAsiento(i);
            asiento.setEstado("Disponible");

            asientos.add(asiento);
        }
        asientoRepositorio.saveAll(asientos);
    }

    @Override
    public void actualizarAsientos(Unidad unidad) {
        Long numAsientos = asientoRepositorio.countByUnidad(unidad);
        if (numAsientos != unidad.getNumAsientos()) {
            List<Asiento> asientos = asientoRepositorio.findAllByUnidad(unidad);
            asientoRepositorio.deleteAll(asientos);
            crearAsientos(unidad);
        }
    }

    @Override
    public List<Asiento> obtenerAsientosByUnidad(Unidad unidad) {
        return asientoRepositorio.findAllByUnidad(unidad);
    }
}
