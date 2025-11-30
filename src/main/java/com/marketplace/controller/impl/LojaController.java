package com.marketplace.controller.impl;

import com.marketplace.controller.ControllerGenerico;
import com.marketplace.dto.loja.LojaComDonoExistenteDTO;
import com.marketplace.dto.loja.LojaComDonoNovoDTO;
import com.marketplace.dto.loja.LojaRespostaDTO;
import com.marketplace.service.LojaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/lojas")
@RequiredArgsConstructor
public class LojaController implements ControllerGenerico {

    private final LojaService lojaService;

    @PostMapping("/com-dono-novo")
    public ResponseEntity<LojaRespostaDTO> criarLojaComDonoNovo(
            @Valid @RequestBody LojaComDonoNovoDTO dto) {
        LojaRespostaDTO lojaRespostaDTO = lojaService.criarLojaComDonoNovo(dto);
        URI location = gerarHeaderLocation(lojaRespostaDTO.id());
        return ResponseEntity.created(location).body(lojaRespostaDTO);
    }

    @PostMapping("/com-dono-existente")
    public ResponseEntity<LojaRespostaDTO> criarLojaComDonoExistente(
            @Valid @RequestBody LojaComDonoExistenteDTO dto) {
        LojaRespostaDTO lojaRespostaDTO = lojaService.criarLojaComDonoExistente(dto);
        URI location = gerarHeaderLocation(lojaRespostaDTO.id());
        return ResponseEntity.created(location).body(lojaRespostaDTO);
    }

    @GetMapping("/ativas")
    public ResponseEntity<List<LojaRespostaDTO>> listarLojasAtivas() {
        return ResponseEntity.ok(lojaService.listarLojasAtivas());
    }

    @GetMapping
    public ResponseEntity<List<LojaRespostaDTO>> listarTodasLojas() {
        return ResponseEntity.ok(lojaService.listarTodasLojas());
    }
}
