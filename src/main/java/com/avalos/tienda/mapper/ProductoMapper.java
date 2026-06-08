package com.avalos.tienda.mapper;

import com.avalos.tienda.dto.ProductoRequestDTO;
import com.avalos.tienda.entity.Producto;
import com.avalos.tienda.dto.ProductoResponseDTO;

public class ProductoMapper {

    public static ProductoResponseDTO toDTO(Producto producto){
        ProductoResponseDTO dto = new ProductoResponseDTO();
        dto.setId(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setDescripcion(producto.getDescripcion());
        dto.setPrecio(producto.getPrecio());
        dto.setCantidad(producto.getCantidad());
        if(producto.getCategoria() != null) {
            dto.setCategoriaId(producto.getCategoria().getId());
            dto.setCategoriaNombre(producto.getCategoria().getNombre());
        } else{
            dto.setCategoriaNombre("Sin categoría");
        }
        return dto;
    }

    public static Producto toEntity(ProductoRequestDTO productoRequestDTO){
        Producto producto = new Producto();
        producto.setNombre(productoRequestDTO.getNombre());
        producto.setDescripcion(productoRequestDTO.getDescripcion());
        producto.setPrecio(productoRequestDTO.getPrecio());
        producto.setCantidad(productoRequestDTO.getCantidad());
        return producto;
    }
}
