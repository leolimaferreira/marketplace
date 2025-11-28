package com.marketplace.dto.pagamento;

import com.marketplace.annotation.FormaPagamentoValida;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record PagamentoCriacaoDTO(
        @FormaPagamentoValida
        String formaPagamento,
        @NotNull(message = "Id do produto é obrigatório")
        UUID pedidoId
) {
}
