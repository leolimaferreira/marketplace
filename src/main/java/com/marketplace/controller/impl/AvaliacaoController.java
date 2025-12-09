package com.marketplace.controller.impl;

import com.marketplace.controller.ControllerGenerico;
import com.marketplace.dto.avaliacao.AvaliacaoAtualizacaoDTO;
import com.marketplace.dto.avaliacao.AvaliacaoCriacaoDTO;
import com.marketplace.dto.avaliacao.AvaliacaoRespostaDTO;
import com.marketplace.service.AvaliacaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/avaliacoes")
@RequiredArgsConstructor
public class AvaliacaoController implements ControllerGenerico {

    private final AvaliacaoService avaliacaoService;

    @PostMapping
    public ResponseEntity<AvaliacaoRespostaDTO> criarAvaliacao(
            @RequestBody @Valid AvaliacaoCriacaoDTO dto,
            @RequestHeader(name = "Authorization") String token) {
        AvaliacaoRespostaDTO avaliacaoRespostaDTO = avaliacaoService.criarAvaliacao(dto, token);
        URI location = gerarHeaderLocation(avaliacaoRespostaDTO.id());
        return ResponseEntity.created(location).body(avaliacaoRespostaDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AvaliacaoRespostaDTO> atualizarAvaliacao(
            @PathVariable("id") UUID id,
            @RequestBody @Valid AvaliacaoAtualizacaoDTO dto,
            @RequestHeader(name = "Authorization") String token) {
        AvaliacaoRespostaDTO avaliacaoRespostaDTO = avaliacaoService.atualizarAvaliacao(id, dto, token);
        return ResponseEntity.ok(avaliacaoRespostaDTO);
    }
}
