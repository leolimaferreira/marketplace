package com.marketplace.dto.produto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ProdutoCriacaoDTO(
        @NotBlank
        @Size(max = 255, message = "Nome pode ter no máximo 255 caracteres")
        String nome,
        @NotBlank
        @Size(max = 1000, message = "Descrição pode ter no máximo 1000 caracteres")
        String descricao,
        @NotNull
        BigDecimal precoCompra,
        @NotNull
        BigDecimal precoVenda,
        @NotNull
        @Min(value = 1, message = "Quantidade deve ser maior ou igual a 1")
        Integer quantidade,
        String imagem
) {
}
