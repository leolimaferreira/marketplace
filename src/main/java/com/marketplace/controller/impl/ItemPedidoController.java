package com.marketplace.controller.impl;

import com.marketplace.controller.ControllerGenerico;
import com.marketplace.dto.itempedido.ItemPedidoCriacaoDTO;
import com.marketplace.dto.itempedido.ItemPedidoRespostaDTO;
import com.marketplace.service.ItemPedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/itens-pedido")
@RequiredArgsConstructor
public class ItemPedidoController implements ControllerGenerico {

    private final ItemPedidoService itemPedidoService;

    @PostMapping
    public ResponseEntity<ItemPedidoRespostaDTO> adicionarItemAoPedido(@RequestBody @Valid ItemPedidoCriacaoDTO dto) {
        ItemPedidoRespostaDTO itemPedidoRespostaDTO = itemPedidoService.adicionarItemAoPedido(dto);
        URI location = gerarHeaderLocation(itemPedidoRespostaDTO.id());
        return ResponseEntity.created(location).body(itemPedidoRespostaDTO);
    }
}
