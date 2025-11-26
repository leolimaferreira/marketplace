package com.marketplace.dto.endereco;

import java.util.UUID;

public record EnderecoRespostaDTO(
        UUID id,
        String logradouro,
        String numero,
        String complemento,
        String bairro,
        String cidade,
        String estado,
        String cep,
        Boolean enderecoPrincipal
) {
}
