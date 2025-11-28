package com.marketplace.controller.impl;

import com.marketplace.controller.ControllerGenerico;
import com.marketplace.dto.loja.LojaComDonoExistenteDTO;
import com.marketplace.dto.loja.LojaComDonoNovoDTO;
import com.marketplace.dto.loja.LojaRespostaDTO;
import com.marketplace.service.LojaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

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
}
