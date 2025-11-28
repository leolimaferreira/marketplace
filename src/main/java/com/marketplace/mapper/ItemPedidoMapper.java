package com.marketplace.mapper;

import com.marketplace.dto.itempedido.ItemPedidoCriacaoDTO;
import com.marketplace.dto.itempedido.ItemPedidoRespostaDTO;
import com.marketplace.exception.NaoEncontradoException;
import com.marketplace.model.ItemPedido;
import com.marketplace.model.Pedido;
import com.marketplace.model.Produto;
import com.marketplace.repository.PedidoRepository;
import com.marketplace.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class ItemPedidoMapper {

    private final ProdutoRepository produtoRepository;
    private final PedidoRepository pedidoRepository;
    private final ProdutoMapper produtoMapper;

    public ItemPedido mapearParaItemPedido(ItemPedidoCriacaoDTO dto) {
        Produto produto = produtoRepository.findByIdAndAtivo(dto.produtoId())
                .orElseThrow(() -> new NaoEncontradoException("Produto não encontrado"));

        Pedido pedido = pedidoRepository.findById(dto.pedidoId())
                .orElseThrow(() -> new NaoEncontradoException("Pedido não encontrado"));

        BigDecimal valorTotal = produto.getPrecoVenda().multiply(BigDecimal.valueOf(dto.quantidade()));

        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setQuantidade(dto.quantidade());
        itemPedido.setProduto(produto);
        itemPedido.setPedido(pedido);
        itemPedido.setValorTotal(valorTotal);

        return itemPedido;
    }

    public ItemPedidoRespostaDTO mapearParaItemPedidoResposta(ItemPedido itemPedido) {
        return new ItemPedidoRespostaDTO(
                itemPedido.getId(),
                itemPedido.getQuantidade(),
                produtoMapper.mapearParaProdutoRespostaDTO(itemPedido.getProduto()),
                itemPedido.getValorTotal(),
                itemPedido.getDataCadastro(),
                itemPedido.getDataAtualizacao()
        );
    }
}
