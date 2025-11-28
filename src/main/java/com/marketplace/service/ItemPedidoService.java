package com.marketplace.service;

import com.marketplace.dto.itempedido.ItemPedidoCriacaoDTO;
import com.marketplace.dto.itempedido.ItemPedidoRespostaDTO;
import com.marketplace.mapper.ItemPedidoMapper;
import com.marketplace.model.ItemPedido;
import com.marketplace.repository.ItemPedidoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemPedidoService {

    private final ItemPedidoRepository itemPedidoRepository;
    private final ItemPedidoMapper itemPedidoMapper;
    private final PedidoService pedidoService;

    @Transactional
    public ItemPedidoRespostaDTO adicionarItemAoPedido(ItemPedidoCriacaoDTO dto) {
        ItemPedido itemPedido = itemPedidoMapper.mapearParaItemPedido(dto);
        pedidoService.adicionarItem(itemPedido);
        return itemPedidoMapper.mapearParaItemPedidoResposta(itemPedidoRepository.save(itemPedido));
    }
}
