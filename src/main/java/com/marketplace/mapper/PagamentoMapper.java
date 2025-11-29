package com.marketplace.mapper;

import com.marketplace.dto.pagamento.PagamentoAtualizacaoDTO;
import com.marketplace.dto.pagamento.PagamentoCriacaoDTO;
import com.marketplace.dto.pagamento.PagamentoRespostaDTO;
import com.marketplace.exception.AtualizacaoStatusInvalidaException;
import com.marketplace.exception.NaoEncontradoException;
import com.marketplace.model.ItemPedido;
import com.marketplace.model.Pagamento;
import com.marketplace.model.Pedido;
import com.marketplace.model.enums.FormaPagamento;
import com.marketplace.model.enums.PagamentoStatus;
import com.marketplace.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

import static com.marketplace.model.enums.PagamentoStatus.CANCELADO;
import static com.marketplace.model.enums.PagamentoStatus.CONCLUIDO;

@Component
@RequiredArgsConstructor
public class PagamentoMapper {

    private final PedidoRepository pedidoRepository;

    public Pagamento mapearParaPagamento(PagamentoCriacaoDTO dto) {
        Pedido pedido = pedidoRepository.findById(dto.pedidoId())
                .orElseThrow(() -> new NaoEncontradoException("Pedido não encontrado"));

        Pagamento pagamento = new Pagamento();
        pagamento.setFormaPagamento(FormaPagamento.valueOf(dto.formaPagamento()));
        pagamento.setPedido(pedido);
        List<ItemPedido> itens = pedido.getItens();
        pagamento.setValor(itens == null ? BigDecimal.ZERO :
                itens.stream()
                        .map(ItemPedido::getValorTotal)
                        .reduce(BigDecimal.ZERO, BigDecimal::add));

        return pagamento;
    }

    public PagamentoRespostaDTO mapearParaPagamentoResposta(Pagamento pagamento) {
        return pagamento == null ? null : new PagamentoRespostaDTO(
                pagamento.getId(),
                pagamento.getFormaPagamento(),
                pagamento.getStatus(),
                pagamento.getValor(),
                pagamento.getDataCriacao(),
                pagamento.getDataAtualizacao()
        );
    }

    public void atualizarPagamento(Pagamento pagamento, PagamentoAtualizacaoDTO dto) {
        if (pagamento.getStatus().toString().equals(CONCLUIDO.toString())) {
            throw new AtualizacaoStatusInvalidaException("Não é possível pendencirar ou cancelar um pagamento já aprovado");
        }

        if (pagamento.getStatus().toString().equals(CANCELADO.toString())) {
            throw new AtualizacaoStatusInvalidaException("Não é possível aprovar ou pendenciar um pagamento já cancelado");
        }

        pagamento.setStatus(PagamentoStatus.valueOf(dto.status()));
    }

}
