package com.avalos.tienda.dto;

import lombok.Data;

@Data
public class ProductoResponseDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private Double precio;
    private Integer cantidad;
    private Long categoriaId;
    private String categoriaNombre;
}
