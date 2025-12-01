package com.marketplace.dto.produto;

import com.marketplace.annotation.PrecoVendaValido;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.UUID;

@PrecoVendaValido
public record ProdutoCriacaoDTO(
        @NotBlank
        @Size(max = 255, message = "Nome pode ter no máximo 255 caracteres")
        String nome,
        @NotBlank
        @Size(max = 1000, message = "Descrição pode ter no máximo 1000 caracteres")
        String descricao,
        @NotNull
        @DecimalMin(value = "0.01", message = "Preço de compra deve ser no mínimo R$00,01")
        BigDecimal precoCompra,
        @NotNull
        @DecimalMin(value = "0.01", message = "Preço de compra deve ser no mínimo R$00,01")
        BigDecimal precoVenda,
        @NotNull
        @Min(value = 1, message = "Quantidade deve ser maior ou igual a 1")
        Integer quantidade,
        String imagem,
        @NotNull
        UUID lojaId
) {
}
