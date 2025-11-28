package com.marketplace.dto.itempedido;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ItemPedidoCriacaoDTO(
        @NotNull(message = "Quantidade é obrigatória")
        @Min(value = 1, message = "Quantidade deve ser maior que zero")
        Integer quantidade,
        @NotNull(message = "Id do produto é obrigatório")
        UUID produtoId,
        @NotNull
        @NotNull(message = "Id do pedido é obrigatório")
        UUID pedidoId
) {
}
