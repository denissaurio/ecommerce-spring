package com.avalos.tienda.controller;

import com.avalos.tienda.dto.CarritoResponseDTO;
import com.avalos.tienda.dto.OrdenItemRequestDTO;
import com.avalos.tienda.service.OrdenService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CarritoController {
    private final OrdenService ordenService;

    public CarritoController(OrdenService ordenService){
        this.ordenService = ordenService;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping("/carrito")
    public ResponseEntity<CarritoResponseDTO> calcularCarrito(@RequestBody @Valid List<OrdenItemRequestDTO> ordenItemRequestDTOList){
        return ResponseEntity.status(HttpStatus.OK).body(ordenService.calcularCarrito(ordenItemRequestDTOList));
    }

}
