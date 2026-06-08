package com.avalos.tienda.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrdenItemRequestDTO {
    @NotNull
    private Long productoId;
    @NotNull
    private Integer cantidad;
}
