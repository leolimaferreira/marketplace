package com.marketplace.dto.produto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record ProdutoRespostaDTO(
        UUID id,
        String nome,
        String descricao,
        BigDecimal precoCompra,
        BigDecimal precoVenda,
        Integer quantidade,
        String imagem,
        LocalDateTime criadoEm,
        LocalDateTime atualizadoEm,
        Boolean ativo
) {
}
