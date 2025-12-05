package com.marketplace.controller.impl;

import com.marketplace.controller.ControllerGenerico;
import com.marketplace.dto.pagamento.PagamentoAtualizacaoDTO;
import com.marketplace.dto.pagamento.PagamentoCriacaoDTO;
import com.marketplace.dto.pagamento.PagamentoRespostaDTO;
import com.marketplace.dto.pedido.PedidoAtualizacaoDTO;
import com.marketplace.service.NotaFiscalService;
import com.marketplace.service.PagamentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
    private final NotaFiscalService notaFiscalService;

    @PostMapping
    public ResponseEntity<PagamentoRespostaDTO> criarPagamento(@RequestBody @Valid PagamentoCriacaoDTO dto) {
        PagamentoRespostaDTO pagamentoRespostaDTO = pagamentoService.criarPagamento(dto);
        URI location = gerarHeaderLocation(pagamentoRespostaDTO.id());
        return ResponseEntity.created(location).body(pagamentoRespostaDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagamentoRespostaDTO> atualizarStatusPagamento(@PathVariable("id") UUID id, @RequestBody @Valid PagamentoAtualizacaoDTO dto) {
        return ResponseEntity.ok(pagamentoService.atualizarStatusPagamento(id, dto, new PedidoAtualizacaoDTO("CONFIRMADO")));
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
        PedidoAtualizacaoDTO pedidoStatus = new PedidoAtualizacaoDTO("CONFIRMADO");
        PagamentoRespostaDTO resposta = pagamentoService.atualizarStatusPagamento(id, dto, pedidoStatus);

        return ResponseEntity.ok(resposta);

    }

    @PostMapping("/{id}/simular-rejeicao")
    public ResponseEntity<PagamentoRespostaDTO> simularRejeicao(@PathVariable UUID id) {
        PagamentoAtualizacaoDTO dto = new PagamentoAtualizacaoDTO("CANCELADO");
        PedidoAtualizacaoDTO pedidoStatus = new PedidoAtualizacaoDTO("CANCELADO");
        return ResponseEntity.ok(pagamentoService.atualizarStatusPagamento(id, dto, pedidoStatus));
    }

    @GetMapping("/{id}/nota-fiscal")
    public ResponseEntity<byte[]> gerarNotaFiscal(
            @PathVariable UUID id,
            @RequestHeader("Authorization") String token
    ) {
        byte[] pdf = notaFiscalService.gerarNotaFiscal(id, token);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("filename", "nota-fiscal-" + id + ".pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(pdf);
    }
}
