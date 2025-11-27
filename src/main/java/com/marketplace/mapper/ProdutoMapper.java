package com.marketplace.mapper;

import com.marketplace.dto.produto.ProdutoCriacaoDTO;
import com.marketplace.dto.produto.ProdutoRespostaDTO;
import com.marketplace.model.Produto;
import org.springframework.stereotype.Component;

@Component
public class ProdutoMapper {

    public Produto mapearParaProduto(ProdutoCriacaoDTO dto) {
        Produto produto = new Produto();
        produto.setNome(dto.nome());
        produto.setDescricao(dto.descricao());
        produto.setPrecoCompra(dto.precoCompra());
        produto.setPrecoVenda(dto.precoVenda());
        produto.setQuantidade(dto.quantidade());
        produto.setImagem(dto.imagem());
        produto.setAtivo(true);
        return produto;
    }

    public ProdutoRespostaDTO mapearParaProdutoRespostaDTO(Produto produto) {
        return new ProdutoRespostaDTO(
                produto.getId(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getPrecoCompra(),
                produto.getPrecoVenda(),
                produto.getQuantidade(),
                produto.getImagem(),
                produto.getDataCadastro(),
                produto.getDataAtualizacao(),
                produto.getAtivo()
        );
    }
}
