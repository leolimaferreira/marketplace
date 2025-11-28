package com.marketplace.dto.pedido;

import com.marketplace.dto.itempedido.ItemPedidoResposta;
import com.marketplace.dto.pagamento.PagamentoRespostaDTO;
import com.marketplace.dto.usuario.cliente.ClienteRespostaDTO;
import com.marketplace.model.enums.PedidoStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record PedidoRespostaDTO(
        UUID id,
        ClienteRespostaDTO cliente,
        List<ItemPedidoResposta> itens,
        PagamentoRespostaDTO pagamento,
        PedidoStatus status,
        LocalDateTime criadoEm,
        LocalDateTime atualizadoEm
) {
}
