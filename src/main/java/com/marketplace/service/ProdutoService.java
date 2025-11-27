package com.marketplace.service;

import com.marketplace.dto.produto.ProdutoCriacaoDTO;
import com.marketplace.dto.produto.ProdutoRespostaDTO;
import com.marketplace.mapper.ProdutoMapper;
import com.marketplace.model.Produto;
import com.marketplace.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.marketplace.repository.specs.ProdutoSpecs.nomeLike;
import static com.marketplace.repository.specs.ProdutoSpecs.precoVendaBetween;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final ProdutoMapper produtoMapper;

    public ProdutoRespostaDTO criarProduto(ProdutoCriacaoDTO dto) {
        return produtoMapper.mapearParaProdutoRespostaDTO(produtoRepository.save(produtoMapper.mapearParaProduto(dto)));
    }

    public Page<ProdutoRespostaDTO> listarProdutos(String nome, BigDecimal precoMin, BigDecimal precoMax, Integer pagina, Integer tamanho) {
        Specification<Produto> specs = null;

        if (nome != null) {
            specs = nomeLike(nome);
        }

        if (precoMin != null || precoMax != null) {
            specs = (specs == null) ? precoVendaBetween(precoMin, precoMax) : specs.and(precoVendaBetween(precoMin, precoMax));
        }


        Pageable pageRequest = Pageable.ofSize(tamanho).withPage(pagina);

        Page<Produto> produtos = produtoRepository.findAll(specs, pageRequest);

        return produtos.map(produtoMapper::mapearParaProdutoRespostaDTO);
    }
}
