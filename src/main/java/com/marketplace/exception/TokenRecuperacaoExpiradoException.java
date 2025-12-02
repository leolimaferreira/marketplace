package com.marketplace.exception;

public class TokenRecuperacaoExpiradoException extends RuntimeException {
    public TokenRecuperacaoExpiradoException(String message) {
        super(message);
    }
}
