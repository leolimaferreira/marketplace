package com.marketplace.service;

import com.marketplace.dto.pagamento.PagamentoCriacaoDTO;
import com.marketplace.dto.pagamento.PagamentoRespostaDTO;
import com.marketplace.mapper.PagamentoMapper;
import com.marketplace.model.Pagamento;
import com.marketplace.repository.PagamentoRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

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
}
