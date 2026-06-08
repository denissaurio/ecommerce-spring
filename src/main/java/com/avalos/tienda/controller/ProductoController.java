package com.avalos.tienda.controller;

import com.avalos.tienda.dto.ProductoRequestDTO;
import com.avalos.tienda.dto.ProductoResponseDTO;
import com.avalos.tienda.entity.Producto;
import com.avalos.tienda.mapper.ProductoMapper;
import com.avalos.tienda.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
public class ProductoController {
    private final ProductoService productoService;

    public ProductoController(ProductoService productoService){
        this.productoService = productoService;
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/productos")
    public Page<ProductoResponseDTO> obtenerProductos(Pageable pageable){
        return productoService.obtenerTodos(pageable);
    }

    @GetMapping("/productos/{id}")
    public ResponseEntity<ProductoResponseDTO> obtenerPorId(@PathVariable Long id){
        Producto producto = productoService.obtenerPorId(id);
        return ResponseEntity.ok(ProductoMapper.toDTO(producto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/productos")
    public ResponseEntity<ProductoResponseDTO> crearProducto(@RequestBody @Valid ProductoRequestDTO productoRequestDTO){
        ProductoResponseDTO productoCreado = productoService.crear(productoRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(productoCreado);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/productos/{id}")
    public ResponseEntity<?> eliminarProducto(@PathVariable Long id){
        productoService.eliminar(id);
        return ResponseEntity.ok(Map.of("mensaje", "Producto eliminado"));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/productos/{id}")
    public ResponseEntity<ProductoResponseDTO> editarProducto(@PathVariable Long id, @RequestBody @Valid ProductoRequestDTO productoRequestDTO){
        ProductoResponseDTO productoEditado = productoService.editar(id, productoRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(productoEditado);
    }
}