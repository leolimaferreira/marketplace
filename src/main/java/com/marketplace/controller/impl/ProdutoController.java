package com.marketplace.controller.impl;

import com.marketplace.controller.ControllerGenerico;
import com.marketplace.dto.produto.ProdutoAtualizacaoDTO;
import com.marketplace.dto.produto.ProdutoCriacaoDTO;
import com.marketplace.dto.produto.ProdutoRespostaDTO;
import com.marketplace.service.ProdutoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.util.UUID;

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

    @GetMapping
    public ResponseEntity<Page<ProdutoRespostaDTO>> listarProdutos(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) BigDecimal precoMin,
            @RequestParam(required = false) BigDecimal precoMax,
            @RequestParam(defaultValue = "0") Integer pagina,
            @RequestParam(defaultValue = "10") Integer tamanho
    ) {
        return ResponseEntity.ok(produtoService.listarProdutos(
                nome,
                precoMin,
                precoMax,
                pagina,
                tamanho
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoRespostaDTO> atualizarProduto(
            @PathVariable(name = "id") UUID id,
            @RequestBody @Valid ProdutoAtualizacaoDTO dto
    ) {
        return ResponseEntity.ok(produtoService.atualizarProduto(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProduto(@PathVariable(name = "id") UUID id) {
        produtoService.deletarProduto(id);
        return ResponseEntity.noContent().build();
    }
}
