package com.marketplace.dto.pagamento;

import com.marketplace.annotation.PagamentoStatusValido;
import jakarta.validation.constraints.NotBlank;

public record PagamentoAtualizacaoDTO(
        @NotBlank(message = "Status de pagamento não pode ser nulo ou vazio")
        @PagamentoStatusValido
        String status
) {
}
