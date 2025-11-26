package com.marketplace.controller.impl;

import com.marketplace.controller.ControllerGenerico;
import com.marketplace.service.ClienteService;
import com.marketplace.dto.usuario.ClienteCriacaoDTO;
import com.marketplace.dto.usuario.ClienteRespostaDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController implements ControllerGenerico {

    private final ClienteService clienteService;

    @PostMapping
    public ResponseEntity<ClienteRespostaDTO> criarCliente(@RequestBody @Valid ClienteCriacaoDTO dto) {
        ClienteRespostaDTO clienteRespostaDTO = clienteService.criarCliente(dto);
        URI location = gerarHeaderLocation(clienteRespostaDTO.id());
        return ResponseEntity.created(location).body(clienteRespostaDTO);
    }

    @GetMapping
    public ResponseEntity<List<ClienteRespostaDTO>> listarClientes() {
        return ResponseEntity.ok(clienteService.listarClientes());
    }
}
