package com.transsurf.pe.repositorio;

import com.transsurf.pe.entidades.Unidad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnidadRepositorio extends JpaRepository<Unidad, Integer> {
    public boolean existsByPlaca(String placa);

}
