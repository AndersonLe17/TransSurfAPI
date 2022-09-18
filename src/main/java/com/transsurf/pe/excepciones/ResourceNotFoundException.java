package com.transsurf.pe.excepciones;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{

    private static final long serialVersionUID = 1L;
    @Getter @Setter
    private String nombreDelRecurso;
    @Getter @Setter
    private String nombreDelCampo;
    @Getter @Setter
    private long valorDelCampo;

    public ResourceNotFoundException(String nombreDelRecurso, String nombreDelCampo, long valorDelCampo) {
        super(String.format("%s no encontrado con: %s = '%s'",nombreDelRecurso,nombreDelCampo,valorDelCampo));
        this.nombreDelRecurso = nombreDelRecurso;
        this.nombreDelCampo = nombreDelCampo;
        this.valorDelCampo = valorDelCampo;
    }
}
