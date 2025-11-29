package com.marketplace.controller.impl;

import com.marketplace.controller.ControllerGenerico;
import com.marketplace.dto.pedido.PedidoCriacaoDTO;
import com.marketplace.dto.pedido.PedidoRespostaDTO;
import com.marketplace.service.PedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor()
public class PedidoController implements ControllerGenerico {

    private final PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<PedidoRespostaDTO> criarPedido(@RequestBody @Valid PedidoCriacaoDTO dto) {
        PedidoRespostaDTO pedidoRespostaDTO = pedidoService.criarPedido(dto);
        URI location = gerarHeaderLocation(pedidoRespostaDTO.id());
        return ResponseEntity.created(location).body(pedidoRespostaDTO);
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<PedidoRespostaDTO>> listarPedidosCliente(@PathVariable(name = "clienteId") UUID clienteId) {
        return ResponseEntity.ok(pedidoService.listarPedidosCliente(clienteId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoRespostaDTO> encontrarPedidoPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(pedidoService.encontrarProdutoPorId(id));
    }
}
