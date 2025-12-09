package com.marketplace.mapper;

import com.marketplace.dto.avaliacao.AvaliacaoCriacaoDTO;
import com.marketplace.dto.avaliacao.AvaliacaoRespostaDTO;
import com.marketplace.exception.NaoEncontradoException;
import com.marketplace.model.Avaliacao;
import com.marketplace.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.marketplace.utils.Constantes.PEDIDO_NAO_ENCONTRADO;

@Component
@RequiredArgsConstructor
public class AvaliacaoMapper {

    private final PedidoMapper pedidoMapper;
    private final PedidoRepository pedidoRepository;

    public Avaliacao mapearParaAvaliacao(AvaliacaoCriacaoDTO dto) {
        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setPedido(pedidoRepository.findById(dto.pedidoId())
                .orElseThrow(() -> new NaoEncontradoException(PEDIDO_NAO_ENCONTRADO)));
        avaliacao.setNota(dto.nota());
        avaliacao.setComentario(dto.comentario());
        avaliacao.setImagens(dto.imagens());
        return avaliacao;
    }

    public AvaliacaoRespostaDTO mapearParaAvaliacaoRespostaDTO(Avaliacao avaliacao) {
        return new AvaliacaoRespostaDTO(
                avaliacao.getId(),
                pedidoMapper.mapearParaPedidoRespostaDTO(avaliacao.getPedido()),
                avaliacao.getNota(),
                avaliacao.getComentario(),
                avaliacao.getImagens(),
                avaliacao.getDataCriacao(),
                avaliacao.getDataAtualizacao()
        );
    }
}
