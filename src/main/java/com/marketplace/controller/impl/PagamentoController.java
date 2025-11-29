package com.marketplace.controller.impl;

import com.marketplace.controller.ControllerGenerico;
import com.marketplace.dto.pagamento.PagamentoAtualizacaoDTO;
import com.marketplace.dto.pagamento.PagamentoCriacaoDTO;
import com.marketplace.dto.pagamento.PagamentoRespostaDTO;
import com.marketplace.service.PagamentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/pagamentos")
@RequiredArgsConstructor
public class PagamentoController implements ControllerGenerico {

    private final PagamentoService pagamentoService;

    @PostMapping
    public ResponseEntity<PagamentoRespostaDTO> criarPagamento(@RequestBody @Valid PagamentoCriacaoDTO dto) {
        PagamentoRespostaDTO pagamentoRespostaDTO = pagamentoService.criarPagamento(dto);
        URI location = gerarHeaderLocation(pagamentoRespostaDTO.id());
        return ResponseEntity.created(location).body(pagamentoRespostaDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagamentoRespostaDTO> atualizarStatusPagamento(
            @PathVariable("id") UUID id,
            @RequestBody @Valid PagamentoAtualizacaoDTO dto) {
        return ResponseEntity.ok(pagamentoService.atualizarStatusPagamento(id, dto));
    }
}
