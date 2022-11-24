package com.transsurf.pe.controlador;

import com.transsurf.pe.dto.CiudadDTO;
import com.transsurf.pe.entidades.Ciudad;
import com.transsurf.pe.excepciones.ResourceNotFoundException;
import com.transsurf.pe.repositorio.CiudadRepositorio;
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

    @Autowired
    private CiudadRepositorio ciudadRepositorio;

    @GetMapping
    public List<CiudadDTO> listarCiudades() {
        return ciudadServicio.obtenerCiudades();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<CiudadDTO> guardarCiudad(@RequestBody CiudadDTO ciudadDTO) {
        return new ResponseEntity<>(ciudadServicio.crearCiudad(ciudadDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{idCiudad}")
    public ResponseEntity<CiudadDTO> obtenerCiudadById(@PathVariable(name = "idCiudad") int idCiudad){
        return ResponseEntity.ok(ciudadServicio.getCiudadById(idCiudad));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{idCiudad}")
    public ResponseEntity<?> actualizarCiudad(@RequestBody CiudadDTO ciudadDTO, @PathVariable(name = "idCiudad") int idCiudad){
        if (ciudadRepositorio.existsByNombre(ciudadDTO.getNombre())) {
            Ciudad ciudad = ciudadRepositorio.findById(idCiudad)
                    .orElseThrow(() -> new ResourceNotFoundException("Ciudad","idCiudad",idCiudad));
            if (!ciudadDTO.getNombre().equals(ciudad.getNombre())) {
                return new ResponseEntity<>("La ciudad ya existe", HttpStatus.BAD_REQUEST);
            }
        }

        return new ResponseEntity<>(ciudadServicio.actualizarCiudad(ciudadDTO,idCiudad),HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{idCiudad}")
    public ResponseEntity<String> elimiarCiudad(@PathVariable(name = "idCiudad") int idCiudad) {
        ciudadServicio.elimarCiudad(idCiudad);

        return new ResponseEntity<>("Ciudad Eliminada con exito",HttpStatus.OK);
    }
}
