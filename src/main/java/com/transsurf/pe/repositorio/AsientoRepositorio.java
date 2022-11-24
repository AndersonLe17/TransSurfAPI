package com.transsurf.pe.repositorio;

import com.transsurf.pe.entidades.Asiento;
import com.transsurf.pe.entidades.Nivel;
import com.transsurf.pe.entidades.Unidad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AsientoRepositorio extends JpaRepository<Asiento, Long> {

    public Long countByUnidad(Unidad unidad);

    public List<Asiento> findAllByUnidad(Unidad unidad);

    public boolean existsByUnidadAndNivel(Unidad unidad, Nivel nivel);

}
