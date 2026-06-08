package com.avalos.tienda.mapper;

import com.avalos.tienda.dto.OrdenRequestDTO;
import com.avalos.tienda.dto.OrdenResponseDTO;
import com.avalos.tienda.entity.Orden;

public class OrdenMapper {
    public static OrdenResponseDTO toDTO(Orden orden){
        OrdenResponseDTO dto = new OrdenResponseDTO();
        dto.setNumeroOrden(orden.getNumeroOrden());
        dto.setDireccionEnvio(orden.getDireccionEnvio());
        dto.setEstado(orden.getEstado());
        dto.setPrecioTotal(orden.getPrecioTotal());
        dto.setFechaCreacion(orden.getFechaCreacion());
        dto.setOrdenItems(
                orden.getOrdenItems()
                        .stream()
                        .map(OrdenItemMapper::toDTO)
                        .toList()
        );
        return dto;
    }

    public static Orden ordenToEntity(OrdenRequestDTO ordenRequestDTO){
        Orden orden = new Orden();
        orden.setDireccionEnvio(ordenRequestDTO.getDireccionEnvio());
        return orden;
    }
}
