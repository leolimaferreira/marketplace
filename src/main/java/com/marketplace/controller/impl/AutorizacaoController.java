package com.marketplace.controller.impl;

import com.marketplace.dto.login.LoginRequestDTO;
import com.marketplace.dto.login.LoginRespostaDTO;
import com.marketplace.dto.recuperacao.RecuperacaoRequestDTO;
import com.marketplace.dto.recuperacao.RecuperacaoRespostaDTO;
import com.marketplace.dto.recuperacao.TrocarSenhaDTO;
import com.marketplace.service.AutorizacaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/autorizacao")
public class AutorizacaoController {

    private final AutorizacaoService autorizacaoService;

    @PostMapping("/login")
    public ResponseEntity<LoginRespostaDTO> login(@RequestBody LoginRequestDTO dto) {
        return ResponseEntity.ok(autorizacaoService.login(dto));
    }

    @PostMapping("/gerar-token-recuperacao")
    public ResponseEntity<RecuperacaoRespostaDTO> generateRecoveryToken(@RequestBody RecuperacaoRequestDTO dto) {
        return ResponseEntity.ok(autorizacaoService.gerarTokenRecuperacao(dto));
    }

    @PostMapping("/trocar-senha")
    public ResponseEntity<Void> trocarSenha(@RequestBody @Valid TrocarSenhaDTO dto) {
        autorizacaoService.trocarSenha(dto);
        return ResponseEntity.noContent().build();
    }
}
