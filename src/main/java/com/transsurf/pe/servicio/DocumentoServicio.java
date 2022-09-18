package com.transsurf.pe.servicio;


import com.transsurf.pe.dto.DocumentoDTO;

import java.util.List;

public interface DocumentoServicio {

    public DocumentoDTO crearDocumento(DocumentoDTO documentoDTO);
    public List<DocumentoDTO> obtenerDocumentos();
    public DocumentoDTO obtenerDocumentoById(int id);
    public DocumentoDTO actualizarDocumento(DocumentoDTO documentoDTO, int id);
    public void eliminarDocumento(int id);

}
