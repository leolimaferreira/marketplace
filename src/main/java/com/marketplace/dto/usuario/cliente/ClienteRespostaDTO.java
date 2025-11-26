package com.marketplace.dto.usuario.cliente;

import com.marketplace.dto.endereco.EnderecoRespostaDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ClienteRespostaDTO(
        UUID id,
        String nome,
        String email,
        String cpf,
        String rg,
        String celular,
        LocalDate dataNascimento,
        String imagem,
        List<EnderecoRespostaDTO> enderecos,
        LocalDateTime criadoEm,
        LocalDateTime atualizadoEm,
        Boolean ativo
) {
}
