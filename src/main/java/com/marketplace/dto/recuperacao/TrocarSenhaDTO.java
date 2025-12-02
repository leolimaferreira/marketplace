package com.marketplace.dto.recuperacao;

import com.marketplace.annotation.SenhaForte;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record TrocarSenhaDTO(
        @NotNull
        UUID tokenId,
        @NotBlank(message = "Nova senha não pode ser vazia")
        @Size(min = 8, max = 20, message = "Nova senha não pode ter menos que 8 e mais que 20 caracteres")
        @SenhaForte
        String novaSenha
) {
}
