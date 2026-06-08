package com.avalos.tienda.service;

import com.avalos.tienda.dto.*;
import com.avalos.tienda.entity.Orden;
import com.avalos.tienda.entity.OrdenItem;
import com.avalos.tienda.entity.Producto;
import com.avalos.tienda.entity.Usuario;
import com.avalos.tienda.exception.ProductoNotFoundException;
import com.avalos.tienda.exception.StockInsuficienteException;
import com.avalos.tienda.exception.UserNotFoundException;
import com.avalos.tienda.mapper.OrdenMapper;
import com.avalos.tienda.repository.OrdenRepository;
import com.avalos.tienda.repository.ProductoRepository;
import com.avalos.tienda.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrdenService {
    private final OrdenRepository ordenRepository;
    private final ProductoRepository productoRepository;
    private final UsuarioRepository usuarioRepository;

    public OrdenService(OrdenRepository ordenRepository, ProductoRepository productoRepository, UsuarioRepository usuarioRepository){
        this.ordenRepository = ordenRepository;
        this.productoRepository = productoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public OrdenResponseDTO crearOrden(OrdenRequestDTO ordenRequestDTO, String email){
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));
        List<OrdenItemRequestDTO> ordenItemsRequest = ordenRequestDTO.getItems();
        List<OrdenItem> items = new ArrayList<>();
        Double precioTotal = 0.0;
        for(OrdenItemRequestDTO item : ordenItemsRequest){
            Producto producto = productoRepository.findById(item.getProductoId()).orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado"));
            if(item.getCantidad() > producto.getCantidad()){
                throw new StockInsuficienteException("Stock insuficiente, solo quedan " + producto.getCantidad() + " disponibles");
            }
            else{
                OrdenItem ordenItem = new OrdenItem();
                Integer stockActual = producto.getCantidad();
                Integer stockReal = stockActual - item.getCantidad();
                producto.setCantidad(stockReal);
                productoRepository.save(producto);
                ordenItem.setProducto(producto);
                ordenItem.setNombre(producto.getNombre());
                ordenItem.setPrecioUnitario(producto.getPrecio());
                ordenItem.setDescripcion(producto.getDescripcion());
                ordenItem.setCantidad(item.getCantidad());
                items.add(ordenItem);
                Double totalProductos = producto.getPrecio() * item.getCantidad();
                precioTotal += totalProductos;
            }
        }
        String numeroOrdenUnico = UUID.randomUUID().toString();
        Orden orden = new Orden();
        for(OrdenItem ordenItem : items){
            ordenItem.setOrden(orden);
        }
        orden.setDireccionEnvio(ordenRequestDTO.getDireccionEnvio());
        orden.setPrecioTotal(precioTotal);
        orden.setNumeroOrden(numeroOrdenUnico);
        orden.setUsuario(usuario);
        orden.setOrdenItems(items);

        Orden ordenSaved = ordenRepository.save(orden);
        return OrdenMapper.toDTO(ordenSaved);
    }

    public List<OrdenResponseDTO> obtenerOrdenesUsuario(String email){
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));
        List<Orden> ordenes = usuario.getOrdenes();
        List<OrdenResponseDTO> ordenesDTO = new ArrayList<>();
        for(Orden orden : ordenes){
            ordenesDTO.add(OrdenMapper.toDTO(orden));
        }
        return ordenesDTO;
    }
    public CarritoResponseDTO calcularCarrito(List<OrdenItemRequestDTO> ordenItemRequestDTOList){
        List<CarritoItemResponseDTO> item = new ArrayList<>();
        Double precioTotal = 0.0;
        for(OrdenItemRequestDTO ordenItem : ordenItemRequestDTOList){
            Producto producto = productoRepository.findById(ordenItem.getProductoId()).orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado"));
            if(ordenItem.getCantidad() > producto.getCantidad()){
                throw new StockInsuficienteException("Stock insuficiente, solo quedan " + producto.getCantidad() + " disponibles");
            }
            else{
                CarritoItemResponseDTO carritoItemResponseDTO = new CarritoItemResponseDTO();
                carritoItemResponseDTO.setNombre(producto.getNombre());
                carritoItemResponseDTO.setDescripcion(producto.getDescripcion());
                carritoItemResponseDTO.setCantidad(ordenItem.getCantidad());
                carritoItemResponseDTO.setPrecio(producto.getPrecio());
                Double totalCantidadProductos = producto.getPrecio() * ordenItem.getCantidad();
                precioTotal += totalCantidadProductos;
                item.add(carritoItemResponseDTO);
            }
        }
        CarritoResponseDTO carritoResponseDTO = new CarritoResponseDTO();
        carritoResponseDTO.setItems(item);
        carritoResponseDTO.setTotal(precioTotal);
        return carritoResponseDTO;
    }
}
