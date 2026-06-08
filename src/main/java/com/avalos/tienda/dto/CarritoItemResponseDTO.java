package com.avalos.tienda.dto;

import lombok.Data;

@Data
public class CarritoItemResponseDTO {
    private String nombre;
    private String descripcion;
    private Integer cantidad;
    private Double precio;
}
