package com.transsurf.pe.repositorio;

import com.transsurf.pe.entidades.Documento;
import com.transsurf.pe.entidades.Rol;
import com.transsurf.pe.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepositorio extends JpaRepository<Usuario,Long> {

    public Optional<Usuario> findByEmail(String email);
    public Optional<Usuario> findByNumDocOrEmail(String numDoc, String email);
    public Optional<Usuario> findByNumDoc(String numDoc);
    public Boolean existsByNumDoc(String numDoc);
    public Boolean existsByEmail(String email);
    public List<Usuario> findAllByRolesNot(Rol rol);
    public List<Usuario> findAllByRolesIs(Rol rol);
    public List<Usuario> findAllByIdUsuarioIn(List<Long> idUsuarios);

    public Optional<Usuario> findByDocumentoAndNumDoc(Documento documento, String numDoc);
}
