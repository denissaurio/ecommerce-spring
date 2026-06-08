package com.avalos.tienda.service;

import com.avalos.tienda.dto.AuthResponseDTO;
import com.avalos.tienda.dto.LoginRequestDTO;
import com.avalos.tienda.entity.Rol;
import com.avalos.tienda.entity.Usuario;
import com.avalos.tienda.exception.InvalidPasswordException;
import com.avalos.tienda.exception.UserNotFoundException;
import com.avalos.tienda.repository.UsuarioRepository;
import com.avalos.tienda.security.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtTokenProvider jwtTokenProvider;
    @InjectMocks
    private AuthService authService;
    @Test
    public void testLoginSuccesful(){ //cuando doy email y pass correctos devuelve jwt
        //crear usuario fake
        Usuario usuarioMock = new Usuario();
        usuarioMock.setId(1L);
        usuarioMock.setNombre("Pedro");
        usuarioMock.setEmail("pedro@email.com");
        usuarioMock.setPassword("$2a$10$Op7/sE5khpNsJMI1w3jRlOLstvD/TfLtjzvP4eAKdBtiTzyuNT5Ga");
        usuarioMock.setRol(Rol.USER);

        //configurar que hace el mock cuando se llama
        //cuando alguien busque por este email, devuelve usuarioMock
        when(usuarioRepository.findByEmail("pedro@email.com")).thenReturn(Optional.of(usuarioMock));

        //cuando alguien verifique la contrasena devuelve true
        when(passwordEncoder.matches("MiPassword123", usuarioMock.getPassword())).thenReturn(true);

        //cuando alguien genere un jwt, devuelve este token
        when(jwtTokenProvider.generateToken(usuarioMock)).thenReturn("token_falso_12345");

        //crear request que enviaria el cliente
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setEmail("pedro@email.com");
        loginRequestDTO.setPassword("MiPassword123");

        //llamar al metodo real que quiero probar
        AuthResponseDTO response = authService.login(loginRequestDTO);

        //verificar que el resultado es lo esperado
        assertNotNull(response); //no es null
        assertEquals("token_falso_12345", response.getToken()); //token es correcto
        assertEquals("pedro@email.com", response.getUsuario().getEmail()); //email es correcto
        assertEquals("Login exitoso", response.getMensaje()); //mensaje es correcto
    }
    @Test
    public void testIncorrectPasswordLogin(){
        Usuario usuarioMock = new Usuario();
        usuarioMock.setId(1L);
        usuarioMock.setNombre("Salmos");
        usuarioMock.setEmail("salmo@email.com");
        usuarioMock.setPassword("$2a$10$Op7/sE5khpNsJMI1w3jRlOLstvD/TfLtjzvP4eAKdBtiTzyuNT5Ga");
        usuarioMock.setRol(Rol.USER);

        when(usuarioRepository.findByEmail("salmo@email.com")).thenReturn(Optional.of(usuarioMock));
        when(passwordEncoder.matches("contrasenaIncorrecta", usuarioMock.getPassword())).thenReturn(false);

        //crear request con contrasena incorrecta
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setEmail("salmo@email.com");
        loginRequestDTO.setPassword("contrasenaIncorrecta");

        assertThrows(InvalidPasswordException.class, () -> authService.login(loginRequestDTO));
    }

    @Test
    public void testLoginUserNotFound() {
        //cuando busquen este email no existe nadie
        when(usuarioRepository.findByEmail("testuser@email.com")).thenReturn(Optional.empty());

        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setEmail("testuser@email.com");
        loginRequestDTO.setPassword("anything");

        assertThrows(UserNotFoundException.class, () -> authService.login(loginRequestDTO));
    }
}
