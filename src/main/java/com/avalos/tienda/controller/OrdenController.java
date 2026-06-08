package com.avalos.tienda.controller;

import com.avalos.tienda.dto.OrdenRequestDTO;
import com.avalos.tienda.dto.OrdenResponseDTO;
import com.avalos.tienda.service.OrdenService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ordenes")
public class OrdenController {
    private final OrdenService ordenService;

    public OrdenController(OrdenService ordenService){
        this.ordenService = ordenService;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping("/ordenes")
    public ResponseEntity<OrdenResponseDTO> crearOrden(@Valid @RequestBody OrdenRequestDTO ordenRequestDTO, @AuthenticationPrincipal String email){
        OrdenResponseDTO ordenCreada = ordenService.crearOrden(ordenRequestDTO, email);
        return ResponseEntity.status(HttpStatus.CREATED).body(ordenCreada);
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/ordenes")
    public List<OrdenResponseDTO> obtenerOrdenes(@AuthenticationPrincipal String email){
        return ordenService.obtenerOrdenesUsuario(email);
    }
}
