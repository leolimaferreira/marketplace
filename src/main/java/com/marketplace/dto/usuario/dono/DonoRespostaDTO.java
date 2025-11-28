package com.marketplace.dto.usuario.dono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record DonoRespostaDTO (
        UUID id,
        String nome,
        String email,
        String cpf,
        String rg,
        String celular,
        LocalDate dataNascimento,
        String imagem,
        LocalDateTime criadoEm,
        LocalDateTime atualizadoEm,
        Boolean ativo
){
}
