package com.avalos.tienda.dto;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RegistroRequestDTO {
    @NotBlank
    private String nombre;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Size(min = 8)
    private String password;
}
