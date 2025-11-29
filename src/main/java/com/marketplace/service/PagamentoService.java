package com.marketplace.service;

import com.marketplace.dto.pagamento.PagamentoAtualizacaoDTO;
import com.marketplace.dto.pagamento.PagamentoCriacaoDTO;
import com.marketplace.dto.pagamento.PagamentoRespostaDTO;
import com.marketplace.exception.NaoEncontradoException;
import com.marketplace.mapper.PagamentoMapper;
import com.marketplace.model.Pagamento;
import com.marketplace.repository.PagamentoRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;
    private final PagamentoMapper pagamentoMapper;

    @Transactional
    public PagamentoRespostaDTO criarPagamento(PagamentoCriacaoDTO dto) {
        Pagamento pagamento = pagamentoMapper.mapearParaPagamento(dto);

        if (dto.formaPagamento().equals("PIX") || dto.formaPagamento().equals("BOLETO")) {
            BigDecimal desconto = pagamento.getValor().multiply(new BigDecimal("0.10"));
            pagamento.setValor(pagamento.getValor().subtract(desconto));
        }

        Pagamento pagamentoSalvo = pagamentoRepository.save(pagamento);
        return pagamentoMapper.mapearParaPagamentoResposta(pagamentoSalvo);
    }

    @Transactional
    public PagamentoRespostaDTO atualizarStatusPagamento(UUID id, PagamentoAtualizacaoDTO dto) {
        Pagamento pagamento = pagamentoRepository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Pagamento não encontrado"));

        pagamentoMapper.atualizarPagamento(pagamento, dto);

        Pagamento pagamentoAtualizado = pagamentoRepository.save(pagamento);
        return pagamentoMapper.mapearParaPagamentoResposta(pagamentoAtualizado);
    }
}
