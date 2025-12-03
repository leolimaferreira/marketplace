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
import java.util.List;
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
    public ResponseEntity<PagamentoRespostaDTO> atualizarStatusPagamento(@PathVariable("id") UUID id, @RequestBody @Valid PagamentoAtualizacaoDTO dto) {
        return ResponseEntity.ok(pagamentoService.atualizarStatusPagamento(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagamentoRespostaDTO> encontrarPagamentoPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(pagamentoService.encontrarPagamentoPorId(id));
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<PagamentoRespostaDTO>> listarPagamentosCliente(@PathVariable(name = "clienteId") UUID clienteId) {
        return ResponseEntity.ok(pagamentoService.listarPagamentosCliente(clienteId));
    }

    @PostMapping("/{id}/simular-aprovacao")
    public ResponseEntity<PagamentoRespostaDTO> simularAprovacao(@PathVariable UUID id, @RequestParam(defaultValue = "3") int segundos) throws InterruptedException {
        Thread.sleep(segundos * 1000L);

        PagamentoAtualizacaoDTO dto = new PagamentoAtualizacaoDTO("CONCLUIDO");
        PagamentoRespostaDTO resposta = pagamentoService.atualizarStatusPagamento(id, dto);

        return ResponseEntity.ok(resposta);

    }

    @PostMapping("/{id}/simular-rejeicao")
    public ResponseEntity<PagamentoRespostaDTO> simularRejeicao(@PathVariable UUID id) {
        PagamentoAtualizacaoDTO dto = new PagamentoAtualizacaoDTO("CANCELADO");
        return ResponseEntity.ok(pagamentoService.atualizarStatusPagamento(id, dto));
    }
}
