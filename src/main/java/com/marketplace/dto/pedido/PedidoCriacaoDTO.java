package com.marketplace.dto.pedido;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record PedidoCriacaoDTO(
        @NotNull
        UUID clienteId,
        @NotNull
        UUID lojaId
) {
}
