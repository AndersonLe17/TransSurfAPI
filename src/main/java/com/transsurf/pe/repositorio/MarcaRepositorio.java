package com.transsurf.pe.repositorio;

import com.transsurf.pe.entidades.Marca;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarcaRepositorio extends JpaRepository<Marca, Integer> {
}
