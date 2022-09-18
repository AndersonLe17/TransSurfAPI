package com.transsurf.pe.repositorio;


import com.transsurf.pe.entidades.Documento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentoRepositorio extends JpaRepository<Documento, Integer> {

}
