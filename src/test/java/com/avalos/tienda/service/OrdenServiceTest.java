package com.avalos.tienda.service;

import com.avalos.tienda.dto.OrdenItemRequestDTO;
import com.avalos.tienda.dto.OrdenRequestDTO;
import com.avalos.tienda.dto.OrdenResponseDTO;
import com.avalos.tienda.entity.Orden;
import com.avalos.tienda.entity.Producto;
import com.avalos.tienda.entity.Usuario;
import com.avalos.tienda.exception.ProductoNotFoundException;
import com.avalos.tienda.exception.StockInsuficienteException;
import com.avalos.tienda.exception.UserNotFoundException;
import com.avalos.tienda.repository.OrdenRepository;
import com.avalos.tienda.repository.ProductoRepository;
import com.avalos.tienda.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class OrdenServiceTest {
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private ProductoRepository productoRepository;
    @Mock
    private OrdenRepository ordenRepository;
    @InjectMocks
    private OrdenService ordenService;

    @Test
    public void testCrearOrden_exitoso() {
        //ARRANGE
        Usuario usuarioMock = new Usuario();
        usuarioMock.setEmail("usuariotest@email.com");
        usuarioMock.setPassword("password");

        Producto productoMock = new Producto();
        productoMock.setId(1L);
        productoMock.setNombre("Teclado");
        productoMock.setPrecio(500.0);
        productoMock.setCantidad(5);

        OrdenItemRequestDTO ordenItemRequestDTOMock = new OrdenItemRequestDTO();
        ordenItemRequestDTOMock.setCantidad(2);
        ordenItemRequestDTOMock.setProductoId(1L);

        OrdenRequestDTO ordenRequestDTOMock = new OrdenRequestDTO();
        ordenRequestDTOMock.setDireccionEnvio("Calle test 123");
        ordenRequestDTOMock.setItems(List.of(ordenItemRequestDTOMock));

        when(usuarioRepository.findByEmail("usuariotest@email.com")).thenReturn(Optional.of(usuarioMock));
        when(productoRepository.findById(1L)).thenReturn(Optional.of(productoMock));
        when(ordenRepository.save(any(Orden.class))).thenAnswer(i -> i.getArgument(0));

        //ACT - llamar al metodo real con los datos que preparé
        OrdenResponseDTO response = ordenService.crearOrden(ordenRequestDTOMock, "usuariotest@email.com");

        //ASSERT - verificar que el resultado tiene lo que espero
        assertNotNull(response.getNumeroOrden());
        assertFalse(response.getNumeroOrden().isEmpty());
        assertEquals("PENDIENTE", response.getEstado());
        assertEquals(1000.0, response.getPrecioTotal());
        assertEquals("Calle test 123", response.getDireccionEnvio());
        assertFalse(response.getOrdenItems().isEmpty());
    }

    @Test
    public void testCrearOrden_stockInsuficiente() {
        Usuario usuarioMock = new Usuario();
        usuarioMock.setEmail("usuariotest@email.com");
        usuarioMock.setPassword("password");

        Producto productoMock = new Producto();
        productoMock.setId(1L);
        productoMock.setNombre("Teclado");
        productoMock.setPrecio(500.0);
        productoMock.setCantidad(5);

        OrdenItemRequestDTO ordenItemRequestDTOMock = new OrdenItemRequestDTO();
        ordenItemRequestDTOMock.setCantidad(10);
        ordenItemRequestDTOMock.setProductoId(1L);

        OrdenRequestDTO ordenRequestDTOMock = new OrdenRequestDTO();
        ordenRequestDTOMock.setDireccionEnvio("Calle test 123");
        ordenRequestDTOMock.setItems(List.of(ordenItemRequestDTOMock));

        when(usuarioRepository.findByEmail("usuariotest@email.com")).thenReturn(Optional.of(usuarioMock));
        when(productoRepository.findById(1L)).thenReturn(Optional.of(productoMock));

        assertThrows(StockInsuficienteException.class, () -> ordenService.crearOrden(ordenRequestDTOMock, "usuariotest@email.com"));
    }

    @Test
    public void testCrearOren_usuarioNoEncontrado() {
        when(usuarioRepository.findByEmail("usuariotest@email.com")).thenReturn(Optional.empty());
        OrdenRequestDTO requestMock = new OrdenRequestDTO();

        assertThrows(UserNotFoundException.class, () -> ordenService.crearOrden(requestMock, "usuariotest@email.com"));
    }

    @Test
    public void testCrearOrden_productoNoEncontrado() {
        Usuario usuarioMock = new Usuario();
        usuarioMock.setEmail("usuariotest@email.com");
        usuarioMock.setPassword("password");

        OrdenItemRequestDTO ordenItemRequestDTOMock = new OrdenItemRequestDTO();
        ordenItemRequestDTOMock.setCantidad(10);
        ordenItemRequestDTOMock.setProductoId(2L);

        when(usuarioRepository.findByEmail("usuariotest@email.com")).thenReturn(Optional.of(usuarioMock));
        when(productoRepository.findById(2L)).thenReturn(Optional.empty());

        OrdenRequestDTO requestMock = new OrdenRequestDTO();
        requestMock.setItems(List.of(ordenItemRequestDTOMock));

        assertThrows(ProductoNotFoundException.class, () -> ordenService.crearOrden(requestMock, "usuariotest@email.com"));
    }

    @Test
    public void testObtenerOrdenesUsuario_exitoso() {
        Orden orden = new Orden();
        orden.setOrdenItems(new ArrayList<>());

        Usuario usuarioMock = new Usuario();
        usuarioMock.setEmail("usuariotest@email.com");
        usuarioMock.setPassword("password");
        usuarioMock.setOrdenes(List.of(orden));

        when(usuarioRepository.findByEmail("usuariotest@email.com")).thenReturn(Optional.of(usuarioMock));

        List<OrdenResponseDTO> response = ordenService.obtenerOrdenesUsuario("usuariotest@email.com");

        assertFalse(response.isEmpty());
    }

    @Test
    public void testObtenerOrdenesUsuario_usuarioNoEncontrado() {
        when(usuarioRepository.findByEmail("usuariotest@email.com")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> ordenService.obtenerOrdenesUsuario("usuariotest@email.com"));
    }
}
