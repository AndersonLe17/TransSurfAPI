package com.transsurf.pe.controlador;

import com.transsurf.pe.dto.MarcaDTO;
import com.transsurf.pe.servicio.MarcaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/marca")
public class MarcaControlador {

    @Autowired
    private MarcaServicio marcaServicio;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<MarcaDTO> listarMarcas() {
        return marcaServicio.obtenerMarcas();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<MarcaDTO> guardarMarca(@RequestBody MarcaDTO marcaDTO) {
        return new ResponseEntity<>(marcaServicio.guardarMarca(marcaDTO), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{idMarca}")
    public ResponseEntity<MarcaDTO> obtenerCiudadById(@PathVariable(name = "idMarca") int idMarca) {
        return ResponseEntity.ok(marcaServicio.getMarcaById(idMarca));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{idMarca}")
    public ResponseEntity<MarcaDTO> actualizarMarca(@RequestBody MarcaDTO marcaDTO, @PathVariable(name = "idMarca") int idMarca) {
        return new ResponseEntity<>(marcaServicio.actualizarMarca(marcaDTO,idMarca),HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{idMarca}")
    public ResponseEntity<String> eliminarMarca(@PathVariable(name = "idMarca") int idMarca) {
        marcaServicio.elimarMarca(idMarca);

        return new ResponseEntity<>("Marca eliminada con Exito",HttpStatus.OK);
    }

}
