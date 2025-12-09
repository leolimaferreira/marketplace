package com.marketplace.dto.avaliacao;

import com.marketplace.dto.pedido.PedidoRespostaDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record AvaliacaoRespostaDTO(
        UUID id,
        PedidoRespostaDTO pedidoRespostaDTO,
        Integer nota,
        String comentario,
        List<String> imagens,
        LocalDateTime criadoEm,
        LocalDateTime atualizadoEm
) {
}
