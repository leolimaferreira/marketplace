package com.marketplace.dto.itempedido;

import com.marketplace.dto.produto.ProdutoRespostaDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record ItemPedidoRespostaDTO(
        UUID id,
        Integer quantidade,
        ProdutoRespostaDTO produto,
        BigDecimal valorTotal,
        LocalDateTime criadoEm,
        LocalDateTime atualizadoEm
) {
}
