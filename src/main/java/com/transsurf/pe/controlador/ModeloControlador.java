package com.transsurf.pe.controlador;

import com.transsurf.pe.dto.ModeloDTO;
import com.transsurf.pe.servicio.ModeloServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/modelo")
public class ModeloControlador {

    @Autowired
    private ModeloServicio modeloServicio;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<ModeloDTO> listarModelos() {
        return modeloServicio.obtenerModelo();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ModeloDTO> guardarModelo(@RequestBody ModeloDTO modeloDTO) {
        return new ResponseEntity<>(modeloServicio.guardarModelo(modeloDTO), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{idModelo}")
    public ResponseEntity<ModeloDTO> obtenerModeloById(@PathVariable(name = "idModelo") int idModelo) {
        return ResponseEntity.ok(modeloServicio.getModeloById(idModelo));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{idModelo}")
    public ResponseEntity<ModeloDTO> actualizarModelo(@RequestBody ModeloDTO modeloDTO, @PathVariable(name = "idModelo") int idModelo) {
        return new ResponseEntity<>(modeloServicio.actualizarModelo(modeloDTO,idModelo),HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{idModelo}")
    public ResponseEntity<String> eliminarModelo(@PathVariable(name = "idModelo") int idModelo) {
        modeloServicio.elimarModelo(idModelo);

        return new ResponseEntity<>("Modelo eliminada con Exito",HttpStatus.OK);
    }

}
