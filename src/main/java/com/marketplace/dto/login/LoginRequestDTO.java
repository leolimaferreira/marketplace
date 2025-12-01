package com.marketplace.dto.login;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequestDTO(
        @Email
        @Size(max = 150, message = "Email não pode ter mais de 150 caracteres.")
        String email,
        @NotBlank(message = "Senha não pode ser em branco")
        String senha
) {
}
