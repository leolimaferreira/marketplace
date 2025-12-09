package com.marketplace.dto.avaliacao;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ListarAvaliacoesProdutoELojaRequest(
        @NotNull(message = "O ID do produto é obrigatório.")
        UUID produtoId,
        @NotNull(message = "O ID da loja é obrigatório.")
        UUID lojaId
) {
}
