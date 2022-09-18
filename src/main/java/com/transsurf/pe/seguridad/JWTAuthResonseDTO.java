package com.transsurf.pe.seguridad;

import lombok.*;

@Getter @Setter
@AllArgsConstructor @RequiredArgsConstructor
public class JWTAuthResonseDTO {

    @NonNull
    private String tokenAcceso;
    private String tipoToken = "Bearer";

}
