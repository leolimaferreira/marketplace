package com.marketplace.dto.recuperacao;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RecuperacaoRequestDTO (
        @NotBlank(message = "Email não pode ser vazio")
        @Email
        @Size(max = 150, message = "Email não pode ter mais de 150 caracteres")
        String email
){
}
