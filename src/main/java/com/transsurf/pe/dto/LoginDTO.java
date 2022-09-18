package com.transsurf.pe.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class LoginDTO {
    private String numDocOrEmail;
    private String password;
}
