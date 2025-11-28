package com.marketplace.dto.pagamento;

import com.marketplace.annotation.FormaPagamentoValida;

import java.util.UUID;

public record PagamentoCriacaoDTO(
        @FormaPagamentoValida
        String formaPagamento,
        UUID pedidoId
) {
}
