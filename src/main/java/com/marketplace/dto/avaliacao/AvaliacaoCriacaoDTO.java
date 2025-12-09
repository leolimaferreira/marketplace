package com.marketplace.dto.avaliacao;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record AvaliacaoCriacaoDTO(
        @NotNull(message = "O ID do pedido é obrigatório")
        UUID pedidoId,
        @NotNull(message = "A nota da avaliação é obrigatória")
        @Min(value = 1, message = "A nota da avaliação deve ser no mínimo 1")
        @Max(value = 5, message = "A nota da avaliação deve ser no máximo 5")
        Integer nota,
        String comentario,
        List<String> imagens
) {
}
