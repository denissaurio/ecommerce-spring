package com.avalos.tienda.dto;

import lombok.Data;
import jakarta.validation.constraints.*;

@Data
public class ProductoRequestDTO {
    @NotBlank
    private String nombre;
    @NotNull
    @DecimalMin("0.0")
    private Double precio;
    @Min(0)
    private Integer cantidad;
    private String descripcion;
    @NotNull
    private Long categoriaId;
}
