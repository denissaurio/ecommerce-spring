package com.avalos.tienda.dto;

import lombok.Data;

@Data
public class OrdenItemResponseDTO {
    private Long productoId;
    private Integer cantidad;
    private String nombre;
    private String descripcion;
    private Double precioUnitario;
}
