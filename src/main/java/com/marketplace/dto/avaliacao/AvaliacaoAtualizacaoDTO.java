package com.marketplace.dto.avaliacao;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import java.util.List;

public record AvaliacaoAtualizacaoDTO(
        @Min(value = 1, message = "A nota da avaliação deve ser no mínimo 1")
        @Max(value = 5, message = "A nota da avaliação deve ser no máximo 5")
        Integer nota,
        @Size(max = 1000, message = "O comentário deve ter no máximo 1000 caracteres")
        String comentario,
        List<String> imagens
) {
}
