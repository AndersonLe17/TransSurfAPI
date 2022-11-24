package com.transsurf.pe.controlador;


import com.transsurf.pe.dto.UnidadDTO;
import com.transsurf.pe.entidades.Marca;
import com.transsurf.pe.entidades.Modelo;
import com.transsurf.pe.entidades.Unidad;
import com.transsurf.pe.excepciones.ResourceNotFoundException;
import com.transsurf.pe.repositorio.MarcaRepositorio;
import com.transsurf.pe.repositorio.ModeloRepositorio;
import com.transsurf.pe.repositorio.UnidadRepositorio;
import com.transsurf.pe.servicio.UnidadServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/unidad")
public class UnidadControlador {

    @Autowired
    private UnidadServicio unidadServicio;

    @Autowired
    private UnidadRepositorio unidadRepositorio;

    @Autowired
    private ModeloRepositorio modeloRepositorio;
    @Autowired
    private MarcaRepositorio marcaRepositorioRepositorio;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<UnidadDTO> listarUnidades() {
        return unidadServicio.obtenerUnidades();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{idModelo}/{idMarca}")
    public ResponseEntity<?> guardarUnidad(@RequestBody UnidadDTO unidadDTO, @PathVariable(name = "idModelo") int idModelo, @PathVariable(name = "idMarca") int idMarca) {
        if (unidadRepositorio.existsByPlaca(unidadDTO.getPlaca())) {
            return new ResponseEntity<>("La placa ya existe", HttpStatus.BAD_REQUEST);
        }
        Modelo modelo = modeloRepositorio.findById(idModelo).
                orElseThrow(() -> new ResourceNotFoundException("Modelo","id",idModelo));
        Marca marca = marcaRepositorioRepositorio.findById(idMarca).
                orElseThrow(() -> new ResourceNotFoundException("Marca","id",idMarca));

        return new ResponseEntity<>(unidadServicio.crearUnidad(unidadDTO, modelo, marca), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{idUnidad}")
    public ResponseEntity<UnidadDTO> obtenerUnidadById(@PathVariable(name = "idUnidad") int idUnidad){
        return new ResponseEntity<>(unidadServicio.getUnidadById(idUnidad), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{idUnidad}/{idModelo}/{idMarca}")
    public ResponseEntity<?> editarUnidad(@RequestBody UnidadDTO unidadDTO, @PathVariable(name = "idUnidad") int idUnidad,
                                          @PathVariable(name = "idModelo") int idModelo, @PathVariable(name = "idMarca") int idMarca) {
        if (unidadRepositorio.existsById(idUnidad)) {
            Unidad unidad = unidadRepositorio.findById(idUnidad).get();
            if (!unidad.getPlaca().equals(unidadDTO.getPlaca()) && unidadRepositorio.existsByPlaca(unidadDTO.getPlaca())) {
                return new ResponseEntity<>("La placa ya existe", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("El idUsuario no existe", HttpStatus.BAD_REQUEST);
        }

        Modelo modelo = modeloRepositorio.findById(idModelo).
                orElseThrow(() -> new ResourceNotFoundException("Modelo","id",idModelo));
        Marca marca = marcaRepositorioRepositorio.findById(idMarca).
                orElseThrow(() -> new ResourceNotFoundException("Marca","id",idMarca));
        return new ResponseEntity<>(unidadServicio.modificarUnidad(unidadDTO, modelo, marca, idUnidad), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{idUnidad}")
    public ResponseEntity<String> eliminarUnidad(@PathVariable(name = "idUnidad") int idUnidad) {
        if (!unidadRepositorio.existsById(idUnidad)) {
            return new ResponseEntity<>("El idUsuario no existe", HttpStatus.BAD_REQUEST);
        }
        unidadServicio.eliminarUnidad(idUnidad);

        return new ResponseEntity<>("Unidad Eliminada con exito",HttpStatus.OK);
    }
}
