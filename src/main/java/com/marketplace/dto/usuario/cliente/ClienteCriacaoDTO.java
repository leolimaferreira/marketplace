package com.marketplace.dto.usuario.cliente;

import com.marketplace.annotation.CpfValido;
import com.marketplace.annotation.SenhaForte;
import com.marketplace.dto.endereco.EnderecoCriacaoDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record ClienteCriacaoDTO(
        @NotBlank(message = "Nome é obrigatório")
        @Size(max = 255, message = "Nome pode ter no máximo 255 caracteres")
        String nome,

        @NotBlank(message = "Email é obrigatório")
        @Email(message = "Email inválido")
        String email,

        @NotBlank(message = "Senha é obrigatória")
        @Size(min = 8, max = 20, message = "Senha deve ter no mínimo 8 caracteres e no máximo 20 caracteres")
        @SenhaForte
        String senha,

        @NotBlank(message = "CPF é obrigatório")
        @CpfValido
        String cpf,

        @NotBlank(message = "RG é obrigatório")
        String rg,

        @NotBlank(message = "Celular é obrigatório")
        @Pattern(regexp = "\\(\\d{2}\\) \\d{4,5}-\\d{4}", message = "Celular inválido")
        String celular,

        @NotNull(message = "Data de nascimento é obrigatória")
        @Past(message = "Data de nascimento deve ser no passado")
        LocalDate dataNascimento,

        String imagem,

        @Valid
        @NotNull(message = "Cliente deve ter pelo menos um endereço")
        EnderecoCriacaoDTO endereco
) {}
