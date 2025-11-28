package com.marketplace.mapper;

import com.marketplace.dto.pedido.PedidoCriacaoDTO;
import com.marketplace.dto.pedido.PedidoRespostaDTO;
import com.marketplace.exception.NaoEncontradoException;
import com.marketplace.model.Cliente;
import com.marketplace.model.Pedido;
import com.marketplace.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.marketplace.utils.Constantes.CLIENTE_NAO_ENCONTRADO;

@Component
@RequiredArgsConstructor
public class PedidoMapper {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;
    private final PagamentoMapper pagamentoMapper;
    private final ItemPedidoMapper itemPedidoMapper;

    public Pedido mapearParaPedido(PedidoCriacaoDTO dto) {
        Cliente cliente = clienteRepository.findByIdAndAtivo(dto.clienteId())
                .orElseThrow(() -> new NaoEncontradoException(CLIENTE_NAO_ENCONTRADO));
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        return pedido;
    }

    public PedidoRespostaDTO mapearParaPedidoRespostaDTO(Pedido pedido) {
        return new PedidoRespostaDTO(
                pedido.getId(),
                clienteMapper.mapearParaClienteRespostaDTO(pedido.getCliente()),
                pedido.getItens().stream().map(itemPedidoMapper::mapearParaItemPedidoResposta).toList(),
                pagamentoMapper.mapearParaPagamentoResposta(pedido.getPagamento()),
                pedido.getValorTotalPedido(),
                pedido.getStatus(),
                pedido.getDataCriacao(),
                pedido.getDataAtualizacao()
        );
    }
}
