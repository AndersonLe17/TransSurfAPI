package com.transsurf.pe.servicio;

import com.transsurf.pe.dto.DocumentoDTO;
import com.transsurf.pe.entidades.Documento;
import com.transsurf.pe.excepciones.ResourceNotFoundException;
import com.transsurf.pe.repositorio.DocumentoRepositorio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentoServicioImp implements DocumentoServicio{

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private DocumentoRepositorio documentoRepositorio;

    @Override
    public DocumentoDTO crearDocumento(DocumentoDTO documentoDTO) {
        Documento documento = mapearEntidad(documentoDTO);

        Documento nuevoDocumento = documentoRepositorio.save(documento);

        DocumentoDTO documentoResponse = mapearDTO(nuevoDocumento);
        return documentoResponse;
    }

    @Override
    public List<DocumentoDTO> obtenerDocumentos() {
        List<Documento> documentos = documentoRepositorio.findAll();
        return documentos.stream().map(documento -> mapearDTO(documento)).collect(Collectors.toList());
    }

    @Override
    public DocumentoDTO obtenerDocumentoById(int id) {
        Documento documento = documentoRepositorio.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Documento","id",id));
        return mapearDTO(documento);
    }

    @Override
    public DocumentoDTO actualizarDocumento(DocumentoDTO documentoDTO, int id) {
        Documento documento = documentoRepositorio.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Documento","id",id));

        documento.setTipo(documentoDTO.getTipo());

        Documento documentoActualizado = documentoRepositorio.save(documento);

        return mapearDTO(documentoActualizado);
    }

    @Override
    public void eliminarDocumento(int id) {
        Documento documento = documentoRepositorio.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Documento","id",id));

        documentoRepositorio.delete(documento);
    }

    // Convierte entidad a DTO
    private DocumentoDTO mapearDTO(Documento documento) {
        DocumentoDTO documentoDTO = modelMapper.map(documento, DocumentoDTO.class);
        return documentoDTO;
    }
    // Convierte de DTO a Entidad
    private Documento mapearEntidad(DocumentoDTO documentoDTO) {
        Documento documento = modelMapper.map(documentoDTO, Documento.class);
        return documento;
    }
}
