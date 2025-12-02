package com.marketplace.dto.recuperacao;

import com.marketplace.dto.usuario.UsuarioRespostaDTO;

import java.time.LocalDateTime;
import java.util.UUID;

public record RecuperacaoRespostaDTO (
        UUID id,
        LocalDateTime dataExpiracao,
        UsuarioRespostaDTO usuario
){
}
