package com.marketplace.mapper;

import com.marketplace.dto.produto.ProdutoAtualizacaoDTO;
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

    public void atualizarProduto(Produto produto, ProdutoAtualizacaoDTO dto) {
        if (dto.nome() != null) produto.setNome(dto.nome());
        if (dto.descricao() != null) produto.setDescricao(dto.descricao());
        if (dto.precoCompra() != null) produto.setPrecoCompra(dto.precoCompra());
        if (dto.precoVenda() != null) produto.setPrecoVenda(dto.precoVenda());
        if (dto.quantidade() != null) produto.setQuantidade(dto.quantidade());
        if (dto.imagem() != null) produto.setImagem(dto.imagem());
    }
}
