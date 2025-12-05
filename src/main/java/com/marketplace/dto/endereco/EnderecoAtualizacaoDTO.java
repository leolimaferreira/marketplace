package com.marketplace.dto.endereco;

import com.marketplace.annotation.CepValido;
import jakarta.validation.constraints.Size;

public record EnderecoAtualizacaoDTO(
        @Size(max = 255, message = "Logradouro pode ter no máximo 255 caracteres")
        String logradouro,

        @Size(max = 10, message = "Número pode ter no máximo 255 caracteres")
        String numero,

        String complemento,

        @Size(max = 255, message = "Cidade pode ter no máximo 255 caracteres")
        String bairro,

        String cidade,

        @Size(min = 2, max = 2, message = "Estado deve ter 2 caracteres")
        String estado,

        @CepValido
        String cep,

        Boolean principal
) {
}
