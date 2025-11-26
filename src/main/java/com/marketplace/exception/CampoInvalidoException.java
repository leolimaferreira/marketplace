package com.marketplace.exception;

import lombok.Getter;

public class CampoInvalidoException extends RuntimeException {

    @Getter
    private final String campo;

    public CampoInvalidoException(String campo, String mensagem) {
        super(mensagem);
        this.campo = campo;
    }
}
