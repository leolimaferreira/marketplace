package com.marketplace.controller.impl;

import com.marketplace.controller.ControllerGenerico;
import com.marketplace.dto.endereco.EnderecoCriacaoDTO;
import com.marketplace.dto.endereco.EnderecoRespostaDTO;
import com.marketplace.service.EnderecoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/enderecos")
@RequiredArgsConstructor
public class EnderecoController implements ControllerGenerico {

    private final EnderecoService enderecoService;

    @PostMapping("/{clienteId}")
    public ResponseEntity<List<EnderecoRespostaDTO>> adicionarEndereco(
            @PathVariable(name = "clienteId") UUID clienteId,
            @RequestBody @Valid List<EnderecoCriacaoDTO> enderecos,
            @RequestHeader("Authorization") String token
    ) {
        List<EnderecoRespostaDTO> resposta = enderecoService.adicionarEndereco(enderecos, clienteId, token);
        URI location = gerarHeaderLocation(resposta.get(0).id());
        return ResponseEntity.created(location).body(resposta);
    }

    @GetMapping("/{clienteId}")
    public ResponseEntity<List<EnderecoRespostaDTO>> listarEnderecosPorCliente(
            @PathVariable(name = "clienteId") UUID clienteId,
            @RequestHeader("Authorization") String token
    ) {
        List<EnderecoRespostaDTO> resposta = enderecoService.listarEnderecosPorCliente(clienteId, token);
        return ResponseEntity.ok(resposta);
    }
}
