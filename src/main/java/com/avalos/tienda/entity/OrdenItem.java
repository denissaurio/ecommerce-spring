package com.avalos.tienda.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "ordenItems")
@Data
public class OrdenItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "orden_id")
    private Orden orden;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    private Integer cantidad;
    private Double precioUnitario;
    private String nombre;
    private String descripcion;
}
