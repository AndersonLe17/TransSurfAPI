package com.transsurf.pe.repositorio;

import com.transsurf.pe.entidades.Ciudad;
import com.transsurf.pe.entidades.Origen;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrigenRepositorio extends JpaRepository<Origen, Integer> {

    public void deleteByCiudadIs(Ciudad ciudad);

}
