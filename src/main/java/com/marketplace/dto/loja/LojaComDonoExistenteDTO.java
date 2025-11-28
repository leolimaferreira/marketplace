package com.marketplace.dto.loja;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LojaComDonoExistenteDTO(
        @NotBlank
        @Size(max = 255, message = "Nome deve ter no máximo 255 caracteres")
        String nome,

        @NotBlank
        @Size(max = 1000, message = "Descrição deve ter no máximo 1000 caracteres")
        String descricao,

        String imagem,

        @NotBlank
        String cnpj,

        @NotBlank
        @Email
        String emailDono
) {}
