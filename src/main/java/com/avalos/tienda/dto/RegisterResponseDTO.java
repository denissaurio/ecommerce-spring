package com.avalos.tienda.dto;

import lombok.Data;

@Data
public class RegisterResponseDTO {
    private String mensaje;
    private UsuarioResponseDTO usuario;
}
