package com.marketplace.controller.impl;

import com.marketplace.controller.ControllerGenerico;
import com.marketplace.dto.produto.ProdutoCriacaoDTO;
import com.marketplace.dto.produto.ProdutoRespostaDTO;
import com.marketplace.service.ProdutoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
public class ProdutoController implements ControllerGenerico {

    private final ProdutoService produtoService;

    @PostMapping
    public ResponseEntity<ProdutoRespostaDTO> criarProduto(@RequestBody @Valid ProdutoCriacaoDTO dto) {
        ProdutoRespostaDTO produtoRespostaDTO = produtoService.criarProduto(dto);
        URI location = gerarHeaderLocation(produtoRespostaDTO.id());
        return ResponseEntity.created(location).body(produtoRespostaDTO);
    }
}
