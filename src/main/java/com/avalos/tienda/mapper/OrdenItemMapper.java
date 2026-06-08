package com.avalos.tienda.mapper;

import com.avalos.tienda.dto.OrdenItemRequestDTO;
import com.avalos.tienda.dto.OrdenItemResponseDTO;
import com.avalos.tienda.entity.OrdenItem;

public class OrdenItemMapper {
    public static OrdenItemResponseDTO toDTO(OrdenItem ordenItem){
        OrdenItemResponseDTO dto = new OrdenItemResponseDTO();
        dto.setProductoId(ordenItem.getId());
        dto.setCantidad(ordenItem.getCantidad());
        dto.setNombre(ordenItem.getNombre());
        dto.setDescripcion(ordenItem.getDescripcion());
        dto.setPrecioUnitario(ordenItem.getPrecioUnitario());
        return dto;
    }
    public static OrdenItem toEntity(OrdenItemRequestDTO ordenItemRequestDTO){
        OrdenItem ordenItem = new OrdenItem();
        ordenItem.setCantidad(ordenItemRequestDTO.getCantidad());
        return ordenItem;
    }
}
