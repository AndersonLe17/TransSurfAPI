package com.transsurf.pe.repositorio;

import com.transsurf.pe.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepositorio extends JpaRepository<Usuario,Long> {

    public Optional<Usuario> findByEmail(String email);
    public Optional<Usuario> findByNumDocOrEmail(String numDoc, String email);
    public Optional<Usuario> findByNumDoc(String numDoc);
    public Boolean existsByNumDoc(String numDoc);
    public Boolean existsByEmail(String email);

}
