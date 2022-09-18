package com.transsurf.pe.repositorio;

import com.transsurf.pe.entidades.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolRepositorio extends JpaRepository<Rol,Integer> {

    public Optional<Rol> findByTipo(String tipo);

}
