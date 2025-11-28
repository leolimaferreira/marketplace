package com.marketplace.service;

import com.marketplace.dto.produto.ProdutoAtualizacaoDTO;
import com.marketplace.dto.produto.ProdutoCriacaoDTO;
import com.marketplace.dto.produto.ProdutoRespostaDTO;
import com.marketplace.exception.NaoEncontradoException;
import com.marketplace.mapper.ProdutoMapper;
import com.marketplace.model.Loja;
import com.marketplace.model.Produto;
import com.marketplace.repository.LojaRepository;
import com.marketplace.repository.ProdutoRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static com.marketplace.repository.specs.ProdutoSpecs.*;
import static com.marketplace.utils.Constantes.PRODUTO_NAO_ENCONTRADO;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final ProdutoMapper produtoMapper;
    private final LojaRepository lojaRepository;

    @Transactional
    public ProdutoRespostaDTO criarProduto(ProdutoCriacaoDTO dto) {
        return produtoMapper.mapearParaProdutoRespostaDTO(produtoRepository.save(produtoMapper.mapearParaProduto(dto)));
    }

    public Page<ProdutoRespostaDTO> listarProdutos(String nome, BigDecimal precoMin, BigDecimal precoMax, Integer pagina, Integer tamanho) {
        Specification<Produto> specs = ativo();

        if (nome != null) {
            specs = specs.and(nomeLike(nome));
        }

        if (precoMin != null || precoMax != null) {
            specs = specs.and(precoVendaBetween(precoMin, precoMax));
        }


        Pageable pageRequest = Pageable.ofSize(tamanho).withPage(pagina);

        Page<Produto> produtos = produtoRepository.findAll(specs, pageRequest);

        return produtos.map(produtoMapper::mapearParaProdutoRespostaDTO);
    }

    @Transactional
    public ProdutoRespostaDTO encontrarProdutoPorId(UUID id) {
        Produto produto = produtoRepository.findByIdAndAtivo(id)
                .orElseThrow(() -> new NaoEncontradoException(PRODUTO_NAO_ENCONTRADO));

        return produtoMapper.mapearParaProdutoRespostaDTO(produto);
    }

    @Transactional
    public ProdutoRespostaDTO atualizarProduto(UUID id, ProdutoAtualizacaoDTO dto) {
        Produto produto = produtoRepository.findByIdAndAtivo(id)
                .orElseThrow(() -> new NaoEncontradoException(PRODUTO_NAO_ENCONTRADO));

        produtoMapper.atualizarProduto(produto, dto);

        Produto produtoAtualizado = produtoRepository.save(produto);

        return produtoMapper.mapearParaProdutoRespostaDTO(produtoAtualizado);
    }

    @Transactional
    public void deletarProduto(UUID id) {
        Produto produto = produtoRepository.findByIdAndAtivo(id)
                .orElseThrow(() -> new NaoEncontradoException(PRODUTO_NAO_ENCONTRADO));

        List<Loja> lojas = lojaRepository.findLojasByProduto(produto);

        lojas.forEach(loja -> {
            loja.getProdutos().remove(produto);
            lojaRepository.save(loja);
        });

        produto.setAtivo(false);
        produtoRepository.save(produto);
    }
}
