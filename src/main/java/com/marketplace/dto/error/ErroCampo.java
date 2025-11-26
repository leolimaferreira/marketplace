package com.marketplace.dto.error;

public record ErroCampo(
        String campo,
        String erro
) {
}