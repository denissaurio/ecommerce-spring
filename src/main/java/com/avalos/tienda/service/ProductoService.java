package com.avalos.tienda.service;

import com.avalos.tienda.dto.ProductoRequestDTO;
import com.avalos.tienda.dto.ProductoResponseDTO;
import com.avalos.tienda.entity.Categoria;
import com.avalos.tienda.entity.Producto;
import com.avalos.tienda.exception.CategoriaNotFoundException;
import com.avalos.tienda.exception.ProductoNotFoundException;
import com.avalos.tienda.repository.CategoriaRepository;
import com.avalos.tienda.repository.ProductoRepository;
import com.avalos.tienda.mapper.ProductoMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductoService {
    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProductoService(ProductoRepository productoRepository, CategoriaRepository categoriaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    /*public List<ProductoResponseDTO> obtenerTodos(){
        return productoRepository.findAll()
                .stream()
                .map(ProductoMapper::toDTO)
                .collect(Collectors.toList());
    }*/
    public Page<ProductoResponseDTO> obtenerTodos(Pageable pageable){
        Page<Producto> productos = productoRepository.findAll(pageable);
        return productos.map(ProductoMapper::toDTO);
    }

    public Producto obtenerPorId(Long id){
        return productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado"));
    }

    public ProductoResponseDTO crear(ProductoRequestDTO productoRequestDTO){
        Categoria categoria = categoriaRepository.findById(productoRequestDTO.getCategoriaId()).orElseThrow(() -> new CategoriaNotFoundException("Categoría no existe"));
        Producto producto = ProductoMapper.toEntity(productoRequestDTO);
        producto.setCategoria(categoria);
        Producto productoGuardado = productoRepository.save(producto);
        return ProductoMapper.toDTO(productoGuardado);
    }

    public void eliminar(Long id){
        if(productoRepository.existsById(id)){
            productoRepository.deleteById(id);
        }
        else{
            throw new ProductoNotFoundException("Producto no encontrado");
        }
    }

    public ProductoResponseDTO editar(Long id, ProductoRequestDTO productoRequestDTO){
        Producto producto = productoRepository.findById(id).orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado"));
        producto.setNombre(productoRequestDTO.getNombre());
        producto.setDescripcion(productoRequestDTO.getDescripcion());
        producto.setPrecio(productoRequestDTO.getPrecio());
        producto.setCantidad(productoRequestDTO.getCantidad());

        Categoria categoria = categoriaRepository.findById(productoRequestDTO.getCategoriaId()).orElseThrow(() -> new CategoriaNotFoundException("Categoría no existe"));
        producto.setCategoria(categoria);

        Producto productoEditado = productoRepository.save(producto);
        return ProductoMapper.toDTO(productoEditado);
    }
}
