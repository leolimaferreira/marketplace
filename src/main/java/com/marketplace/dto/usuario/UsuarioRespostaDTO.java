package com.marketplace.dto.usuario;

import com.marketplace.model.enums.Cargo;

import java.util.UUID;

public record UsuarioRespostaDTO(
        UUID id,
        String nome,
        String email,
        Cargo cargo
) {
}
