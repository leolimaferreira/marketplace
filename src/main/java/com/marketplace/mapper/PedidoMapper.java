package com.marketplace.mapper;

import com.marketplace.dto.pedido.PedidoCriacaoDTO;
import com.marketplace.dto.pedido.PedidoRespostaDTO;
import com.marketplace.exception.AtualizacaoStatusInvalidaException;
import com.marketplace.exception.NaoEncontradoException;
import com.marketplace.model.Cliente;
import com.marketplace.model.Loja;
import com.marketplace.model.Pedido;
import com.marketplace.model.enums.PedidoStatus;
import com.marketplace.repository.ClienteRepository;
import com.marketplace.repository.LojaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.marketplace.model.enums.PedidoStatus.*;
import static com.marketplace.utils.Constantes.CLIENTE_NAO_ENCONTRADO;
import static com.marketplace.utils.Constantes.LOJA_NAO_ENCONTRADA;

@Component
@RequiredArgsConstructor
public class PedidoMapper {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;
    private final PagamentoMapper pagamentoMapper;
    private final ItemPedidoMapper itemPedidoMapper;
    private final LojaMapper lojaMapper;
    private final LojaRepository lojaRepository;

    public Pedido mapearParaPedido(PedidoCriacaoDTO dto) {
        Cliente cliente = clienteRepository.findByIdAndAtivo(dto.clienteId())
                .orElseThrow(() -> new NaoEncontradoException(CLIENTE_NAO_ENCONTRADO));

        Loja loja = lojaRepository.findById(dto.lojaId())
                .orElseThrow(() -> new NaoEncontradoException(LOJA_NAO_ENCONTRADA));

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setLoja(loja);
        return pedido;
    }

    public PedidoRespostaDTO mapearParaPedidoRespostaDTO(Pedido pedido) {
        return new PedidoRespostaDTO(
                pedido.getId(),
                clienteMapper.mapearParaClienteRespostaDTO(pedido.getCliente()),
                lojaMapper.mapearParaLojaResposta(pedido.getLoja()),
                pedido.getItens().stream().map(itemPedidoMapper::mapearParaItemPedidoResposta).toList(),
                pagamentoMapper.mapearParaPagamentoResposta(pedido.getPagamento()),
                pedido.getValorTotalPedido(),
                pedido.getStatus(),
                pedido.getDataCriacao(),
                pedido.getDataAtualizacao()
        );
    }

    public void atualizarPedido(Pedido pedido, PedidoStatus novoStatus) {
        if (pedido.getStatus().equals(PENDENTE) && !novoStatus.equals(CONFIRMADO)) {
            throw new AtualizacaoStatusInvalidaException("Um pedido PENDENTE só pode ser atualizado para CONFIRMADO");
        }

        if (pedido.getStatus().equals(CONFIRMADO) && !novoStatus.equals(EM_PREPARACAO)) {
            throw new AtualizacaoStatusInvalidaException("Um pedido CONFIRMADO só pode ser atualizado para EM PREPARAÇÃO");
        }

        if (pedido.getStatus().equals(EM_PREPARACAO) && !novoStatus.equals(ENVIADO)) {
            throw new AtualizacaoStatusInvalidaException("Um pedido EM PREPARAÇÃO só pode ser atualizado para ENVIADO");
        }

        if (pedido.getStatus().equals(ENVIADO) && !novoStatus.equals(ENTREGUE)) {
            throw new AtualizacaoStatusInvalidaException("Um pedido ENVIADO só pode ser atualizado para ENTREGUE");
        }

        if (pedido.getStatus().equals(ENVIADO) || pedido.getStatus().equals(ENTREGUE) || pedido.getStatus().equals(CANCELADO)) {
            throw new AtualizacaoStatusInvalidaException("Um pedido ENVIADO, ENTREGUE ou CANCELADO não pode ser atualizado");
        }

        pedido.setStatus(novoStatus);
    }
}
