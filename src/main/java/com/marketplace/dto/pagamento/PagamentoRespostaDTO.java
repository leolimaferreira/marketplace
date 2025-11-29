package com.marketplace.dto.pagamento;

import com.marketplace.model.enums.FormaPagamento;
import com.marketplace.model.enums.PagamentoStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record PagamentoRespostaDTO(
        UUID id,
        FormaPagamento formaPagamento,
        PagamentoStatus status,
        BigDecimal valorPago,
        LocalDateTime criadoEm,
        LocalDateTime atualizadoEm
) {
}
