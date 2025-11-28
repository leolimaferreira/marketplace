package com.marketplace.service;

import com.marketplace.dto.itempedido.ItemPedidoCriacaoDTO;
import com.marketplace.dto.itempedido.ItemPedidoRespostaDTO;
import com.marketplace.exception.NaoEncontradoException;
import com.marketplace.exception.QuantidadeInsuficienteException;
import com.marketplace.mapper.ItemPedidoMapper;
import com.marketplace.model.ItemPedido;
import com.marketplace.model.Pedido;
import com.marketplace.model.Produto;
import com.marketplace.repository.ItemPedidoRepository;
import com.marketplace.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static com.marketplace.utils.Constantes.PRODUTO_NAO_ENCONTRADO;

@Service
@RequiredArgsConstructor
public class ItemPedidoService {

    private final ItemPedidoRepository itemPedidoRepository;
    private final ItemPedidoMapper itemPedidoMapper;
    private final PedidoService pedidoService;
    private final ProdutoRepository produtoRepository;

    @Transactional
    public ItemPedidoRespostaDTO adicionarItemAoPedido(ItemPedidoCriacaoDTO dto) {
        ItemPedido itemPedido;
        Produto produto = produtoRepository.findByIdAndAtivo(dto.produtoId())
                .orElseThrow(() -> new NaoEncontradoException(PRODUTO_NAO_ENCONTRADO));

        if (produto.getQuantidade() < dto.quantidade()) {
            throw new QuantidadeInsuficienteException("Temos apenas " + produto.getQuantidade() + " " + produto.getNome() + " em estoque");
        }

        int novaQuantidadeProduto = produto.getQuantidade() - dto.quantidade();
        produto.setQuantidade(novaQuantidadeProduto);
        produtoRepository.save(produto);

        if (!itemPedidoRepository.existsByProdutoIdAndPedidoId(dto.produtoId(), dto.pedidoId())) {
            itemPedido = itemPedidoMapper.mapearParaItemPedido(dto);
            pedidoService.adicionarItem(itemPedido);
        } else {
            itemPedido = itemPedidoRepository.findByProdutoIdAndPedidoId(dto.produtoId(), dto.pedidoId())
                    .orElseThrow(() -> new NaoEncontradoException("Item não encontrado"));

            int novaQuantidade = itemPedido.getQuantidade() + dto.quantidade();
            itemPedido.setQuantidade(novaQuantidade);

            BigDecimal novoValorTotal = itemPedido.getProduto().getPrecoVenda()
                    .multiply(BigDecimal.valueOf(novaQuantidade));
            itemPedido.setValorTotal(novoValorTotal);

            Pedido pedido = itemPedido.getPedido();
            BigDecimal valorAdicionado = itemPedido.getProduto().getPrecoVenda()
                    .multiply(BigDecimal.valueOf(dto.quantidade()));
            pedido.setValorTotalPedido(pedido.getValorTotalPedido().add(valorAdicionado));
        }

        return itemPedidoMapper.mapearParaItemPedidoResposta(itemPedidoRepository.save(itemPedido));
    }
}
