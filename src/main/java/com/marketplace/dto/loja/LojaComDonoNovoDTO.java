package com.marketplace.dto.loja;

import com.marketplace.dto.usuario.dono.DonoCriacaoDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record LojaComDonoNovoDTO(
        @NotBlank
        @Size(max = 255, message = "Nome deve ter no máximo 255 caracteres")
        String nome,

        @NotBlank
        @Size(max = 1000, message = "Descrição deve ter no máximo 1000 caracteres")
        String descricao,

        String imagem,

        @NotBlank
        String cnpj,

        @NotNull
        @Valid
        DonoCriacaoDTO dono
) {}
