package com.marketplace.mapper;

import com.marketplace.dto.loja.LojaAtualizacaoDTO;
import com.marketplace.dto.loja.LojaComDonoExistenteDTO;
import com.marketplace.dto.loja.LojaComDonoNovoDTO;
import com.marketplace.dto.loja.LojaRespostaDTO;
import com.marketplace.exception.NaoEncontradoException;
import com.marketplace.model.Dono;
import com.marketplace.model.Loja;
import com.marketplace.repository.DonoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

import static com.marketplace.utils.Constantes.DONO_NAO_ENCONTRADO;

@Component
@RequiredArgsConstructor
public class LojaMapper {

    private final DonoMapper donoMapper;
    private final ProdutoMapper produtoMapper;
    private final DonoRepository donoRepository;

    public Loja mapearParaLoja(LojaComDonoExistenteDTO dto) {
        Loja loja = new Loja();
        loja.setNome(dto.nome());
        loja.setDescricao(dto.descricao());
        loja.setImagem(dto.imagem());
        loja.setCnpj(dto.cnpj());
        return loja;
    }

    public Loja mapearParaLoja(LojaComDonoNovoDTO dto) {
        Loja loja = new Loja();
        loja.setNome(dto.nome());
        loja.setDescricao(dto.descricao());
        loja.setImagem(dto.imagem());
        loja.setCnpj(dto.cnpj());
        return loja;
    }

    public LojaRespostaDTO mapearParaLojaResposta(Loja loja) {
        return new LojaRespostaDTO(
                loja.getId(),
                loja.getNome(),
                loja.getDescricao(),
                loja.getImagem(),
                loja.getCnpj(),
                donoMapper.mapearParaDonoResposta(loja.getDono()),
                loja.getProdutos() == null ? new ArrayList<>() : loja.getProdutos().stream()
                        .map(produtoMapper::mapearParaProdutoRespostaDTO)
                        .toList(),
                loja.getDataCadastro(),
                loja.getDataAtualizacao(),
                loja.getAtivo()
        );
    }

    public void atualizarLoja(Loja loja, LojaAtualizacaoDTO dto) {
        if (dto.emailDono() != null) {
            Dono novoDono = donoRepository.findByEmailAndAtivo(dto.emailDono())
                    .orElseThrow(() -> new NaoEncontradoException(DONO_NAO_ENCONTRADO));

            loja.setDono(novoDono);
        }

        if (dto.nome() != null) loja.setNome(dto.nome());
        if (dto.descricao() != null) loja.setDescricao(dto.descricao());
        if (dto.imagem() != null) loja.setImagem(dto.imagem());
    }
}
