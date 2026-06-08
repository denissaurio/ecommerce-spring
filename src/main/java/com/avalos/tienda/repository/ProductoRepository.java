package com.avalos.tienda.repository;

import com.avalos.tienda.entity.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    Page<Producto> findAll(Pageable pageable);
}
