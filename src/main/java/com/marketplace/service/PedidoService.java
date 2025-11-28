package com.marketplace.service;

import com.marketplace.dto.pedido.PedidoCriacaoDTO;
import com.marketplace.dto.pedido.PedidoRespostaDTO;
import com.marketplace.mapper.PedidoMapper;
import com.marketplace.model.ItemPedido;
import com.marketplace.model.Pedido;
import com.marketplace.repository.PedidoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        pedidoRepository.save(pedido);
    }
}
