package com.avalos.tienda.mapper;

import com.avalos.tienda.dto.RegisterResponseDTO;
import com.avalos.tienda.dto.RegistroRequestDTO;
import com.avalos.tienda.dto.UsuarioResponseDTO;
import com.avalos.tienda.entity.Usuario;

public class UserMapper {
    public static UsuarioResponseDTO toDTOUser(Usuario usuario){
        UsuarioResponseDTO userDTO = new UsuarioResponseDTO();
        userDTO.setId(usuario.getId());
        userDTO.setNombre(usuario.getNombre());
        userDTO.setEmail(usuario.getEmail());
        userDTO.setRol(usuario.getRol());
        return userDTO;
    }

    public static RegisterResponseDTO toDTORegister(Usuario userCreated){
        RegisterResponseDTO registerDTO = new RegisterResponseDTO();
        registerDTO.setMensaje("Usuario registrado exitosamente");
        registerDTO.setUsuario(toDTOUser(userCreated));
        return registerDTO;
    }

    public static Usuario toEntity(RegistroRequestDTO registroRequestDTO){
        Usuario user = new Usuario();
        user.setNombre(registroRequestDTO.getNombre());
        user.setEmail(registroRequestDTO.getEmail());
        user.setPassword(registroRequestDTO.getPassword());
        return user;
    }
}

