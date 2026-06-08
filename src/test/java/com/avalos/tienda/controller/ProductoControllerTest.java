package com.avalos.tienda.controller;

import com.avalos.tienda.entity.Categoria;
import com.avalos.tienda.entity.Producto;
import com.avalos.tienda.repository.CategoriaRepository;
import com.avalos.tienda.repository.ProductoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;

    private Long productoId;

    @BeforeEach
    public void setup() {
        Categoria categoria = new Categoria();
        categoria.setNombre("Electronica");
        categoriaRepository.save(categoria);

        Producto producto = new Producto();
        producto.setCategoria(categoria);
        producto.setNombre("Laptop");
        producto.setPrecio(5000.0);
        Producto productoGuardado = productoRepository.save(producto);
        productoId = productoGuardado.getId();
    }

    @AfterEach
    public void cleanup() {
        productoRepository.deleteAll();
        categoriaRepository.deleteAll();
    }

    @Test
    @WithMockUser
    public void testGetProductos_notFound() throws Exception {
        mockMvc.perform(get("/productos/77"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    public void testGetProductos_found() throws Exception {
        mockMvc.perform(get("/productos/{id}", productoId))
                .andExpect(status().isOk());
    }
}