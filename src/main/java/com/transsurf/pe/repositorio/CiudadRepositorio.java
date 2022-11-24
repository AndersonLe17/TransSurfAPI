package com.transsurf.pe.repositorio;

import com.transsurf.pe.entidades.Ciudad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CiudadRepositorio extends JpaRepository<Ciudad, Integer> {
    public boolean existsByNombre(String nombre);
}
