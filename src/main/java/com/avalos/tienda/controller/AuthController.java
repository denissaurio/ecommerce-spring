package com.avalos.tienda.controller;

import com.avalos.tienda.dto.*;
import com.avalos.tienda.entity.Usuario;
import com.avalos.tienda.mapper.UserMapper;
import com.avalos.tienda.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @GetMapping("/users")
    public Page<UsuarioResponseDTO> showUsers(Pageable pageable){
        return authService.obtenerTodos(pageable);
    }
    @GetMapping("/users/{id}")
    public ResponseEntity<UsuarioResponseDTO> showUserbyID(@PathVariable Long id){
        Usuario user = authService.showUserById(id);
        return ResponseEntity.ok(UserMapper.toDTOUser(user));
    }
    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> userRegister(@RequestBody @Valid RegistroRequestDTO registroRequestDTO){
        RegisterResponseDTO userCreated = authService.createUser(registroRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody @Valid LoginRequestDTO loginRequestDTO){
        AuthResponseDTO response = authService.login(loginRequestDTO);
        return ResponseEntity.ok(response);
    }
}