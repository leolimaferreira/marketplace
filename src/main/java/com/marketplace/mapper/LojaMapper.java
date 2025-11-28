package com.marketplace.mapper;

import com.marketplace.dto.loja.LojaComDonoExistenteDTO;
import com.marketplace.dto.loja.LojaComDonoNovoDTO;
import com.marketplace.dto.loja.LojaRespostaDTO;
import com.marketplace.model.Loja;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class LojaMapper {

    private final DonoMapper donoMapper;
    private final ProdutoMapper produtoMapper;

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
}
