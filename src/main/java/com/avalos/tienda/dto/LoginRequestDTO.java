package com.avalos.tienda.dto;

import lombok.Data;
import jakarta.validation.constraints.*;

@Data
public class LoginRequestDTO {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;
}
