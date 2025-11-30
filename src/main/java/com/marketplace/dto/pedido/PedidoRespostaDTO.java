package com.marketplace.dto.pedido;

import com.marketplace.dto.itempedido.ItemPedidoRespostaDTO;
import com.marketplace.dto.loja.LojaRespostaDTO;
import com.marketplace.dto.pagamento.PagamentoRespostaDTO;
import com.marketplace.dto.usuario.cliente.ClienteRespostaDTO;
import com.marketplace.model.enums.PedidoStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record PedidoRespostaDTO(
        UUID id,
        ClienteRespostaDTO cliente,
        LojaRespostaDTO loja,
        List<ItemPedidoRespostaDTO> itens,
        PagamentoRespostaDTO pagamento,
        BigDecimal valorTotalPedido,
        PedidoStatus status,
        LocalDateTime criadoEm,
        LocalDateTime atualizadoEm
) {
}
