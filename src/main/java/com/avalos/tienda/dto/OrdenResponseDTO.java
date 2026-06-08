package com.avalos.tienda.dto;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
public class OrdenResponseDTO {
    private String numeroOrden;
    private List<OrdenItemResponseDTO> ordenItems;
    private String direccionEnvio;
    private String estado;
    private Double precioTotal;
    private LocalDateTime fechaCreacion;
}
