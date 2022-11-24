package com.transsurf.pe.repositorio;

import com.transsurf.pe.entidades.Ciudad;
import com.transsurf.pe.entidades.Destino;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DestinoRepositorio extends JpaRepository<Destino, Long> {

    public void deleteByCiudadIs(Ciudad ciudad);

}
