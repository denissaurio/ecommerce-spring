package com.avalos.tienda.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "ordenes")
@Data
public class Orden {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private LocalDateTime fechaCreacion;

    private String numeroOrden;
    private String direccionEnvio;
    private Double precioTotal;
    private String estado = "PENDIENTE";

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @OneToMany(mappedBy = "orden", cascade = CascadeType.ALL)
    private List<OrdenItem> ordenItems;
}
