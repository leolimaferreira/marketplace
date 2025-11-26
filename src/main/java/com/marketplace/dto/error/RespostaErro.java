package com.marketplace.dto.error;

import org.springframework.http.HttpStatus;

import java.util.List;

public record RespostaErro(
        int status,
        String mensagem,
        List<ErroCampo> erro
) {

    public static RespostaErro of(HttpStatus status, String mensagem, List<ErroCampo> erros) {
        return new RespostaErro(status.value(), mensagem, erros);
    }
}
