package com.avalos.tienda.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class OrdenRequestDTO {
    @NotBlank
    private String direccionEnvio;
    @NotNull
    private List<OrdenItemRequestDTO> items;
}
