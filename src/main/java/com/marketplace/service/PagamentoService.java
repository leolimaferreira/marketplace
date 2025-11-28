package com.marketplace.service;

import com.marketplace.dto.pagamento.PagamentoCriacaoDTO;
import com.marketplace.dto.pagamento.PagamentoRespostaDTO;
import com.marketplace.mapper.PagamentoMapper;
import com.marketplace.model.Pagamento;
import com.marketplace.repository.PagamentoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;
    private final PagamentoMapper pagamentoMapper;

    @Transactional
    public PagamentoRespostaDTO criarPagamento(PagamentoCriacaoDTO dto) {
        Pagamento pagamento = pagamentoMapper.mapearParaPagamento(dto);
        Pagamento pagamentoSalvo = pagamentoRepository.save(pagamento);
        return pagamentoMapper.mapearParaPagamentoResposta(pagamentoSalvo);
    }
}
