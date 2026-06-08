package com.avalos.tienda.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class CarritoResponseDTO {
    private Double total;
    private List<CarritoItemResponseDTO> items;
}
