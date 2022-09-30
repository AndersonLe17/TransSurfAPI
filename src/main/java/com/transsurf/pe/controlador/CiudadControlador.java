package com.transsurf.pe.controlador;

import com.transsurf.pe.dto.CiudadDTO;
import com.transsurf.pe.servicio.CiudadServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/ciudad")
public class CiudadControlador {

    @Autowired
    private CiudadServicio ciudadServicio;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<CiudadDTO> listarCiudades() {
        return ciudadServicio.obtenerCiudades();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<CiudadDTO> guardarCiudad(@RequestBody CiudadDTO ciudadDTO) {
        return new ResponseEntity<>(ciudadServicio.crearCiudad(ciudadDTO), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{idCiudad}")
    public ResponseEntity<CiudadDTO> obtenerCiudadById(@PathVariable(name = "idCiudad") int idCiudad){
        return ResponseEntity.ok(ciudadServicio.getCiudadById(idCiudad));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{idCiudad}")
    public ResponseEntity<CiudadDTO> actualizarCiudad(@RequestBody CiudadDTO ciudadDTO, @PathVariable(name = "idCiudad") int idCiudad){
        return new ResponseEntity<>(ciudadServicio.actualizarCiudad(ciudadDTO,idCiudad),HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{idCiudad}")
    public ResponseEntity<String> elimiarCiudad(@PathVariable(name = "idCiudad") int idCiudad) {
        ciudadServicio.elimarCiudad(idCiudad);

        return new ResponseEntity<>("Ciudad Eliminada con exito",HttpStatus.OK);
    }
}
