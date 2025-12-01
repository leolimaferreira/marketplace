package com.marketplace.dto.loja;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record LojaAtualizacaoDTO (
        @Size(max = 255, message = "Nome deve ter no máximo 255 caracteres")
        String nome,
        @Size(max = 1000, message = "Descrição deve ter no máximo 1000 caracteres")
        String descricao,
        String imagem,
        @Email
        String emailDono

){
}
