package com.transsurf.pe.servicio;

import com.transsurf.pe.dto.IndicadorDTO;
import com.transsurf.pe.entidades.Reserva;
import com.transsurf.pe.entidades.Rol;
import com.transsurf.pe.repositorio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IndicadorServicioImp implements IndicadorServicio{

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private ProgramacionRepositorio programacionRepositorio;
    @Autowired
    private ReservaRepositorio reservaRepositorio;
    @Autowired
    private CiudadRepositorio ciudadRepositorio;
    @Autowired
    private TripulacionRepositorio tripulacionRepositorio;

    @Override
    public IndicadorDTO firstIndicators() {
        IndicadorDTO indicadorDTO = new IndicadorDTO();

        indicadorDTO.setNumEmpleados(usuarioRepositorio.findAllByRolesNot(new Rol(2,"ROLE_CLIENT")).size());
        indicadorDTO.setNumProgramaciones(programacionRepositorio.findAll().size());

        java.util.Date date = new java.util.Date();
        date.setDate(0);
        Date fechaInicio = new Date(date.getTime());

        List<Reserva> reservas = reservaRepositorio.findAllByFechaAfter(fechaInicio);
        List<Double> costos = reservas.stream().map(reserva -> reserva.getAsientoProgramacion().getProgramacion().getCosto()).collect(Collectors.toList());
        indicadorDTO.setIngresoMes(costos.stream().reduce(0.0, (subtotal, element) -> subtotal + element));
        indicadorDTO.setCiudades(ciudadRepositorio.findAll().size());

        return indicadorDTO;
    }

    @Override
    public List<Object> areaIndicator() {
        List<Object> report = reservaRepositorio.findReport();
        return report;
    }

    @Override
    public List<Object> barIndicator1() {
        List<Object> report = programacionRepositorio.findReportUnidades();
        return report;
    }

    @Override
    public List<Object> pieIndicator() {
        List<Object> report = programacionRepositorio.findReportCiudades();
        return report;
    }

    @Override
    public List<Object> barIndicator2() {
        List<Object> report = tripulacionRepositorio.findReportTripulacion();
        return report;
    }
}
