package com.transsurf.pe.controlador;

import com.transsurf.pe.servicio.IndicadorServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/indicador")
public class IndicadorControlador {

    @Autowired
    private IndicadorServicio indicadorServicio;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/first")
    public ResponseEntity<?> firstIndicators() {
        return new ResponseEntity<>(indicadorServicio.firstIndicators(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/area")
    public ResponseEntity<?> areaIndicators() {
        return new ResponseEntity<>(indicadorServicio.areaIndicator(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/bar1")
    public ResponseEntity<?> barIndicators1() {
        return new ResponseEntity<>(indicadorServicio.barIndicator1(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/pie")
    public ResponseEntity<?> pieIndicators() {
        return new ResponseEntity<>(indicadorServicio.pieIndicator(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/bar2")
    public ResponseEntity<?> barIndicators2() {
        return new ResponseEntity<>(indicadorServicio.barIndicator2(), HttpStatus.OK);
    }

}
