package com.transsurf.pe.servicio;

import com.transsurf.pe.dto.ReservaDTO;
import com.transsurf.pe.entidades.AsientoProgramacion;
import com.transsurf.pe.entidades.Reserva;
import com.transsurf.pe.entidades.Usuario;
import com.transsurf.pe.repositorio.ReservaRepositorio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservaServicioImp implements ReservaServicio{

    @Autowired
    private ReservaRepositorio reservaRepositorio;
    @Autowired
    private AsientoProgramacionServicio asientoProgramacionServicio;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ReservaDTO crearReserva(AsientoProgramacion asientoProgramacion, Usuario usuario) {
        Reserva reserva = new Reserva();
        reserva.setAsientoProgramacion(asientoProgramacion);
        reserva.setUsuario(usuario);
        java.util.Date date = new java.util.Date();
        reserva.setFecha(new Date(date.getTime()));
        reserva.setHora(new Time(date.getTime()));

        Reserva nuevaReserva = reservaRepositorio.save(reserva);
        asientoProgramacionServicio.actualizarEstadoAsiento(asientoProgramacion, "Ocupado");

        return mapearDTO(nuevaReserva);
    }

    @Override
    public List<ReservaDTO> listarReservasByUsuario(Usuario usuario) {
        List<Reserva> reservas = reservaRepositorio.findAllByUsuario(usuario);
        return reservas.stream().map(reserva -> mapearDTO(reserva)).collect(Collectors.toList());
    }

    //Mapear Entidad a DTO
    private ReservaDTO mapearDTO(Reserva reserva) {
        ReservaDTO reservaDTO = modelMapper.map(reserva, ReservaDTO.class);
        return reservaDTO;
    }
    //Mapear DTO a Entidad
    private Reserva mapearEntidad(ReservaDTO reservaDTO) {
        Reserva reserva = modelMapper.map(reservaDTO, Reserva.class);
        return reserva;
    }

}
