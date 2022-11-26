package com.transsurf.pe.repositorio;

import com.transsurf.pe.entidades.Reserva;
import com.transsurf.pe.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservaRepositorio extends JpaRepository<Reserva, Long> {

    public List<Reserva> findAllByUsuario(Usuario usuario);

}
