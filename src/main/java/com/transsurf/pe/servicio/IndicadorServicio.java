package com.transsurf.pe.servicio;

import com.transsurf.pe.dto.IndicadorDTO;

import java.util.List;

public interface IndicadorServicio {
    public IndicadorDTO firstIndicators();

    public List<Object> areaIndicator();

    public List<Object> barIndicator1();

    public List<Object> pieIndicator();

    public List<Object> barIndicator2();
}
