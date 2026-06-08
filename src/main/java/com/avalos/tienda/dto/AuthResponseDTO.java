package com.avalos.tienda.dto;

import lombok.Data;

@Data
public class AuthResponseDTO {
    private String token;
    private String mensaje;
    private UsuarioResponseDTO usuario;
}
