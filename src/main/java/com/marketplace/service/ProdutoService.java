package com.marketplace.service;

import com.marketplace.dto.produto.ProdutoCriacaoDTO;
import com.marketplace.dto.produto.ProdutoRespostaDTO;
import com.marketplace.mapper.ProdutoMapper;
import com.marketplace.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final ProdutoMapper produtoMapper;

    public ProdutoRespostaDTO criarProduto(ProdutoCriacaoDTO dto) {
        return produtoMapper.mapearParaProdutoRespostaDTO(produtoRepository.save(produtoMapper.mapearParaProduto(dto)));
    }
}
