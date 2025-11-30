package com.marketplace.dto.pedido;

import com.marketplace.annotation.PedidoStatusValido;
import jakarta.validation.constraints.NotBlank;

public record PedidoAtualizacaoDTO (
        @PedidoStatusValido
        @NotBlank
        String status
) {
}
