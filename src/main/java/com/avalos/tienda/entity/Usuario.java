package com.avalos.tienda.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Data
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;
    private String password;
    private String nombre;
    private Rol rol;
    @CreationTimestamp
    private LocalDateTime fechaCreacion;

    @OneToMany(mappedBy = "usuario")
    private List<Orden> ordenes;
}
