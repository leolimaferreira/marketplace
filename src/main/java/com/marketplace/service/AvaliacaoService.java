package com.marketplace.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.marketplace.dto.avaliacao.AvaliacaoAtualizacaoDTO;
import com.marketplace.dto.avaliacao.AvaliacaoCriacaoDTO;
import com.marketplace.dto.avaliacao.AvaliacaoRespostaDTO;
import com.marketplace.exception.NaoAutorizadoException;
import com.marketplace.exception.NaoEncontradoException;
import com.marketplace.exception.PedidoJaAvaliadoException;
import com.marketplace.exception.PedidoNaoEntregueException;
import com.marketplace.mapper.AvaliacaoMapper;
import com.marketplace.model.Avaliacao;
import com.marketplace.model.Pedido;
import com.marketplace.repository.AvaliacaoRepository;
import com.marketplace.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.marketplace.utils.Constantes.AVALIACAO_NAO_ENCONTRADA;
import static com.marketplace.utils.Constantes.PEDIDO_NAO_ENCONTRADO;

@Service
@RequiredArgsConstructor
public class AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;
    private final AvaliacaoMapper avaliacaoMapper;
    private final PedidoRepository pedidoRepository;

    @Transactional
    public AvaliacaoRespostaDTO criarAvaliacao(AvaliacaoCriacaoDTO dto, String token) {
        Pedido pedido = pedidoRepository.findById(dto.pedidoId())
                .orElseThrow(() -> new NaoEncontradoException(PEDIDO_NAO_ENCONTRADO));

        if (!pedido.getStatus().toString().equals("ENTREGUE")) {
            throw new PedidoNaoEntregueException("Somente pedidos entregues podem ser avaliados");
        }

        if (Boolean.TRUE.equals(pedido.getAvaliado())) {
            throw new PedidoJaAvaliadoException("Pedido já foi avaliado");
        }

        DecodedJWT decodedJWT = JWT.decode(token);

        if (!pedido.getCliente().getId().toString().equals(decodedJWT.getSubject())) {
            throw new NaoAutorizadoException("Somente o cliente que realizou o pedido pode avaliá-lo");
        }

        Avaliacao avaliacao = avaliacaoMapper.mapearParaAvaliacao(dto);
        Avaliacao avaliacaoSalva = avaliacaoRepository.save(avaliacao);

        return avaliacaoMapper.mapearParaAvaliacaoRespostaDTO(avaliacaoSalva);
    }

    @Transactional
    public AvaliacaoRespostaDTO atualizarAvaliacao(UUID id, AvaliacaoAtualizacaoDTO dto, String token) {
        Avaliacao avaliacao = avaliacaoRepository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException(AVALIACAO_NAO_ENCONTRADA));

        DecodedJWT decodedJWT = JWT.decode(token);

        if (!avaliacao.getPedido().getCliente().getId().toString().equals(decodedJWT.getSubject())) {
            throw new NaoAutorizadoException("Somente o cliente que realizou o pedido pode atualizar a avaliação");
        }

        avaliacaoMapper.atualizarAvaliacao(avaliacao, dto);
        Avaliacao avaliacaoAtualizada = avaliacaoRepository.save(avaliacao);

        return avaliacaoMapper.mapearParaAvaliacaoRespostaDTO(avaliacaoAtualizada);
    }
}
