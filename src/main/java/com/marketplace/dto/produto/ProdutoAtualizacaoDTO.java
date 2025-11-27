package com.marketplace.dto.produto;

import com.marketplace.annotation.PrecoVendaValido;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@PrecoVendaValido
public record ProdutoAtualizacaoDTO(
        @Size(max = 255, message = "Nome pode ter no máximo 255 caracteres")
        String nome,
        @Size(max = 1000, message = "Descrição pode ter no máximo 1000 caracteres")
        String descricao,
        @DecimalMin(value = "0.01", message = "Preço de compra deve ser no mínimo R$00,01")
        BigDecimal precoCompra,
        @DecimalMin(value = "0.01", message = "Preço de compra deve ser no mínimo R$00,01")
        BigDecimal precoVenda,
        @Min(value = 1, message = "Quantidade deve ser maior ou igual a 1")
        Integer quantidade,
        String imagem
) {
}
