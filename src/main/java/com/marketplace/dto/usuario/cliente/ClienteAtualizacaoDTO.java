package com.marketplace.dto.usuario.cliente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ClienteAtualizacaoDTO(
        @Size(max = 255, message = "Nome pode ter no máximo 255 caracteres")
        String nome,
        @Email
        String email,
        @Pattern(regexp = "\\(\\d{2}\\) \\d{4,5}-\\d{4}", message = "Celular inválido")
        String celular,
        String imagem
) {
}
