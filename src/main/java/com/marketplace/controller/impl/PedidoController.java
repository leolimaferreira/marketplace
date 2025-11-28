package com.marketplace.controller.impl;

import com.marketplace.controller.ControllerGenerico;
import com.marketplace.dto.pedido.PedidoCriacaoDTO;
import com.marketplace.dto.pedido.PedidoRespostaDTO;
import com.marketplace.service.PedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

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
}
