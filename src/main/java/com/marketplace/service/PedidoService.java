package com.marketplace.service;

import com.marketplace.dto.pedido.PedidoCriacaoDTO;
import com.marketplace.dto.pedido.PedidoRespostaDTO;
import com.marketplace.mapper.PedidoMapper;
import com.marketplace.model.ItemPedido;
import com.marketplace.model.Pedido;
import com.marketplace.repository.PedidoRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final PedidoMapper pedidoMapper;

    @Transactional
    public PedidoRespostaDTO criarPedido(PedidoCriacaoDTO dto) {
        Pedido pedido = pedidoMapper.mapearParaPedido(dto);
        Pedido pedidoSalvo = pedidoRepository.save(pedido);
        return pedidoMapper.mapearParaPedidoRespostaDTO(pedidoSalvo);
    }

    @Transactional
    public void adicionarItem(ItemPedido itemPedido) {
        Pedido pedido = itemPedido.getPedido();
        pedido.getItens().add(itemPedido);

        BigDecimal novoValorTotalPedido = pedido.getValorTotalPedido().add(itemPedido.getValorTotal());
        pedido.setValorTotalPedido(novoValorTotalPedido);

        pedidoRepository.save(pedido);
    }

    @Transactional(readOnly = true)
    public List<PedidoRespostaDTO> listarPedidosCliente(UUID clienteId) {
        return pedidoRepository
                .findAllByClienteId(clienteId)
                .stream()
                .map(pedidoMapper::mapearParaPedidoRespostaDTO)
                .toList();
    }
}
