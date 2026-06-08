package com.avalos.tienda.service;

import com.avalos.tienda.dto.*;
import com.avalos.tienda.entity.Rol;
import com.avalos.tienda.entity.Usuario;
import com.avalos.tienda.exception.InvalidPasswordException;
import com.avalos.tienda.exception.UsedEmailException;
import com.avalos.tienda.exception.UserNotFoundException;
import com.avalos.tienda.mapper.UserMapper;
import com.avalos.tienda.repository.UsuarioRepository;
import com.avalos.tienda.security.JwtTokenProvider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AuthService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider){
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public Page<UsuarioResponseDTO> obtenerTodos(Pageable pageable){
        Page<Usuario> users = usuarioRepository.findAll(pageable);
        return users.map(UserMapper::toDTOUser);
    }

    public Usuario showUserById(Long id){
        return usuarioRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));
    }

    public RegisterResponseDTO createUser(RegistroRequestDTO registroRequestDTO){
        Optional<Usuario> userOpt = usuarioRepository.findByEmail(registroRequestDTO.getEmail());
        if(userOpt.isPresent()){
            throw new UsedEmailException("Email " + registroRequestDTO.getEmail() + " ya está en uso");
        }
        else{
            Usuario user = UserMapper.toEntity(registroRequestDTO);
            user.setRol(Rol.USER);
            user.setPassword(passwordEncoder.encode(registroRequestDTO.getPassword()));
            Usuario userCreated = usuarioRepository.save(user);
            return UserMapper.toDTORegister(userCreated);
        }
    }
    public AuthResponseDTO login(LoginRequestDTO loginRequestDTO){
        Usuario user = usuarioRepository.findByEmail(loginRequestDTO.getEmail()).orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));
        if(passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword())){
            String token = jwtTokenProvider.generateToken(user);
            AuthResponseDTO response = new AuthResponseDTO();
            response.setToken(token);
            response.setMensaje("Login exitoso");
            response.setUsuario(UserMapper.toDTOUser(user));
            return response;
        }
        else{
            throw new InvalidPasswordException("Contraseña incorrecta");
        }
    }
}
