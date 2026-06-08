package com.avalos.tienda.dto;

import com.avalos.tienda.entity.Rol;
import lombok.Data;

@Data
public class UsuarioResponseDTO {
    private Long id;
    private String email;
    private String nombre;
    private Rol rol;
}
