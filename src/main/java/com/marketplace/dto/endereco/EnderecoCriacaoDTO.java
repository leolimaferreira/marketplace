package com.marketplace.dto.endereco;

import jakarta.validation.constraints.*;

public record EnderecoCriacaoDTO(
        @NotBlank(message = "Logradouro é obrigatório")
        @Size(max = 255, message = "Logradouro pode ter no máximo 255 caracteres")
        String logradouro,

        @NotBlank(message = "Número é obrigatório")
        @Size(max = 10, message = "Número pode ter no máximo 255 caracteres")
        String numero,

        @Size(max = 255, message = "Complemento pode ter no máximo 255 caracteres")
        String complemento,

        @NotBlank(message = "Bairro é obrigatório")
        @Size(max = 255, message = "Cidade pode ter no máximo 255 caracteres")
        String bairro,

        @NotBlank(message = "Cidade é obrigatória")
        String cidade,

        @NotBlank(message = "Estado é obrigatório")
        @Size(min = 2, max = 2, message = "Estado deve ter 2 caracteres")
        String estado,

        @NotBlank(message = "CEP é obrigatório")
        @Pattern(regexp = "\\d{5}-\\d{3}", message = "CEP inválido")
        String cep,

        Boolean principal
) {}

