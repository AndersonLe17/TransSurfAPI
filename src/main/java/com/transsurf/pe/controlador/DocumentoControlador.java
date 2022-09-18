package com.transsurf.pe.controlador;

import com.transsurf.pe.dto.DocumentoDTO;
import com.transsurf.pe.servicio.DocumentoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/documento")
public class DocumentoControlador {

    @Autowired
    private DocumentoServicio documentoServicio;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<DocumentoDTO> guardarDocumento(@RequestBody DocumentoDTO documentoDTO){
        return new ResponseEntity<>(documentoServicio.crearDocumento(documentoDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public List<DocumentoDTO> listarDocumentos() {
        return documentoServicio.obtenerDocumentos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentoDTO> obtenerDocumentoById(@PathVariable(name = "id") int id) {
        return ResponseEntity.ok(documentoServicio.obtenerDocumentoById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<DocumentoDTO> actualizarDocumento(@RequestBody DocumentoDTO documentoDTO, @PathVariable(name = "id") int id) {
        DocumentoDTO documentoResponse = documentoServicio.actualizarDocumento(documentoDTO,id);

        return new ResponseEntity<>(documentoResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarDocumento(@PathVariable(name = "id") int id) {
        documentoServicio.eliminarDocumento(id);

        return new ResponseEntity<>("Documento Eliminado con exito",HttpStatus.OK);
    }
}
